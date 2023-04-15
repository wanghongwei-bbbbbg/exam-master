package com.mz.auth.service.impl;

import com.mz.auth.entity.Question;
import com.mz.auth.entity.QuestionXztOptions;
import com.mz.auth.mapper.QuestionMapper;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.service.QuestionService;
import com.mz.auth.util.CommonUtils;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionMapper;

    /**
     * 分页查询数据
     * @param questionQuery
     * @return
     */
    @Override
    public PageList listPage(QuestionQuery questionQuery) {
        PageList pageList = new PageList();
        //查询总的条数
        pageList.setTotal(questionMapper.queryTotal(questionQuery));
        //查询每页的数据
        pageList.setRows(questionMapper.queryData(questionQuery));
        return pageList;
    }

    /**
     * 保存问题的方法   涉及两张表
     * Status状态  CreateTime创建时间   CreatorId创建者
     * @param question
     */
    @Override
    @Transactional  //涉及两张表，添加事务
    public void addQuestion(Question question) {
        //第一张表exam_questionbank
        //（1）把题目 保存在 题库表exam_questionbank
        question.setStatus(0L);
        question.setCreateTime(new Date());
        //返回主键id，用于把 选项ABCD 保存进 表exam_xzt_options
        question.setCreatorId(CommonUtils.getLoginUser().getId());
        //此时question对象不仅有前台传来的参数，还有设置的字段 Status状态  CreateTime创建时间   CreatorId创建者
        questionMapper.addQuestion(question);

        //第二张表exam_xzt_options 选择题选项表 abcd
        //如果是选择题 则把选择题的 选项 保存进 选项表
        if (question.getQ_typeid() == 1L) {//（1.题型 选择题）如果是选择题
            QuestionXztOptions questionXztOptions = question.getQuestionXztOptions();//question.getQuestionXztOptions()（2.选项 ABCD）
            questionXztOptions.setQuestionId(question.getId()); //即questionId=id 把两张表联系起来
            //此时的questionXztOptions包括了questionId
            questionMapper.addXztOptions(questionXztOptions);
        }
    }

    /**
     * 根据问题id查询问题   返回值类型为Question实体表
     * @param qid
     * @return
     */
    @Override
    public Question queryQuestionByQid(Long qid) {
        return questionMapper.queryQuestionByQid(qid);
    }

    /**
     * 根据id修改问题方法
     * 不管什么题型 都去删除一下 选择题选项数据  如果传过来的是选择题，再去插入  先删除再插入
     * @param question
     */
    @Override
    @Transactional //涉及两张表，添加事务
    public void editQuestion(Question question) {
        question.setCreatorId(CommonUtils.getLoginUser().getId());//重新获取登录用户的id(即题目创建者)，防止其他人登录把题目改了，所以重新获取
        //不管什么题型 都去删除一下 选择题选项数据
        //修改问题
        questionMapper.updateQuestion(question);
        //如果是选择题
        questionMapper.deleteXztOptions(question.getId());
        if (question.getQ_typeid() == 1L) {
            QuestionXztOptions questionXztOptions = question.getQuestionXztOptions();
            questionXztOptions.setQuestionId(question.getId());
            questionMapper.addXztOptions(questionXztOptions);
        }
    }

    /**
     * 删除问题数据,如果是选择题还要删除选项数据
     * @param id
     */
    @Override
    @Transactional //涉及两张表，添加事务
    public void deleteQuestion(Long id) {
        //不管什么题,都尝试删除选项数据
        questionMapper.deleteXztOptions(id);
        //如何再删除问题表数据
        questionMapper.deleteQuestion(id);

    }
}
