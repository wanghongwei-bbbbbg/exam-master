package com.mz.auth.service.impl;

import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperGengerateVO;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.entity.TypeTotalVO;
import com.mz.auth.mapper.PaperMapper;
import com.mz.auth.mapper.QuestionMapper;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.service.PaperService;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    PaperMapper paperMapper;

    /**
     * 分页查询试卷数据
     * @param paperQuery
     * @return
     */
    @Override
    public PageList listPage(PaperQuery paperQuery) {
        /* 把查询得到的总的条数、每页的数据、封装到pageList对象里面去 */
        PageList pageList=new PageList();
        //查询总的条数
        Long total = paperMapper.queryTotal(paperQuery);
        pageList.setTotal(total);
        //查询每页的数据
        List<Paper> papers = paperMapper.queryData(paperQuery);
        pageList.setRows(papers);
        //封装到pageList对象里面去
        return pageList;
    }

    /**
     * 新增试卷
     * @param paper
     */
    @Override
    public void addPaper(Paper paper) {
        paper.setStatus(0); //0为正在使用，1为已禁用
        paper.setCreateTime(new Date());
        paperMapper.addPaper(paper);
    }

    /**
     * 删除试卷及其组题记录
     * @param id
     */
    @Override
    @Transactional //涉及多表删除,开启事务
    public void deletePaper(Long id) {
        //删除试卷对应的组题记录
        paperMapper.deletePaperQuestion(id);
        //删除试卷
        paperMapper.deletePaper(id);
    }

    /**
     * 修改试卷
     * @param paper
     */
    @Override
    public void editSavePaper(Paper paper) {
        paperMapper.editSavePaper(paper);
    }

    /**
     * 查询所有试卷数据,前端组成树状图
     * @return
     */
    @Override
    public List<Paper> queryPaper() {
        List<Paper> papers = paperMapper.queryPaper();
        return papers;
    }

    /**
     *试卷组卷： 根据试卷id 查询试卷对应的问题
     * @param paperId
     * @return
     */
    @Override
    public List<PaperQuestion> queryQuestionByPaperId(Long paperId) {
        return paperMapper.queryQuestionByPaperId(paperId);
    }

    /**
     * 手动组卷 逻辑
     *   （1）先删除试卷对应的问题（因为试卷名称不变，组成新的试卷，就要把原来的试卷的题目删掉，才能组成新的试卷）
     *   （2）再把问题 批量插入试卷对应的问题表 （因为一张试卷对应多个问题，所以要完成批量插入）
     * @param paperQuestion
     */
    @Override
    @Transactional
    public void diyPaperQuestion(PaperQuestion paperQuestion) {
        //（1）先删除试卷对应的问题
        paperMapper.deletePaperQuestion(paperQuestion.getPaperId());
        //（2）再把问题 插入试卷对应的问题表 完成批量插入 [{paperId:1111,questionId:123},{paperId:1111,questionId:124}]
        //一个paperId对应多个questionId 即一张试卷对应多个问题   //stream流循环取出每一个id（即问题id）
        List<Map> params = paperQuestion.getQuestionIdsList().stream().map(question -> {//取出question实体表对象 的每一项即每一条问题的id
            Map mp = new HashMap();
            mp.put("paperId", paperQuestion.getPaperId());
            mp.put("questionId", question.getId());
            return mp;
        }).collect(Collectors.toList());
        paperMapper.insertBatchPaperQuestion(params);//批量插入
    }

    /**
     * 预览试卷方法
     * @param paperId
     * @return
     */
    @Override
    public PaperGengerateVO previewPaper(Long paperId) {
        return paperMapper.previewPaper(paperId);
    }

    /**
     * 查询题型的总数
     * @return
     */
    @Override
    public List<TypeTotalVO> queryTypeTotal() {
        return paperMapper.queryTypeTotal();
    }

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 随机组卷  业务实现逻辑思路：
     * 如 （前台输入选择题数量5道到后台，后台数据库选择题共10道，从10道中随机选5道）
     * xztNum 5 --》10(111,112,115,888,789,222,333,444,555,666)
     *  思路：
     *       ( 1)获取前台传过来的参数 ： 如选择题数量5道 填空题3 判断1 简答题2
     *      （2）查询数据库所有选择题（题型）的 id的数量： 10(111,112,115,888,789,222,333,444,555,666)--->根据 题型的id 去查
     *      （3）从查询出结果里面 随机选择5 个id ： (根据前端传递数量，循环多少次，每次从上面选择一个id，剔除已选中的id，防止重复)
     *      （4）再保存数据(批量插入)
     * @param mp
     */
    @Override
    @Transactional
    public void randomPaperQuestion(Map mp) {
        //一会传递到后台的  所有问题（包括选择题，判断题，填空题，简答题）的id，即ids包括所有的题型（问题类型）
        List ids = new ArrayList();

        //（1）获取前台传过来的5个参数    mp.get("paperId")默认是string类型，需要转long类型 Long.valueOf（ ）
        Long paperId =   Long.valueOf((String)mp.get("paperId"));
        Long xztNum =   Long.valueOf((String)mp.get("xztNum"));
        Long tktNum =   Long.valueOf((String)mp.get("tktNum"));
        Long pdtNum =   Long.valueOf((String)mp.get("pdtNum"));
        Long jdtNum =   Long.valueOf((String)mp.get("jdtNum"));
        //（2）查询出所有的选择题的id 如数组[111,112,115,888，222,333,444,555,666，999]  根据题型id去查问题, 如题型1为选择题，选择题id有{11,35,..}
        List xztIds = questionMapper.queryQuestionIdByTypeId(1L);
        // 从查询出结果里面 随机选择id
        //选择题
        for (int i = 0; i < xztNum; i++) {//循环遍历取出数组[111,112,115,888，222,333,444,555,666，999]的每一个
            //Random().nextInt（int n）方法  该值介于[0,10)的区间
            //即如Random().nextInt（10），随机取出的值为0到9，如为3，则 xztIds.get（3 ）值为888
            Object target =  xztIds.get(new Random().nextInt(xztIds.size())); //888
            ids.add(target);//把888添加进ids
            xztIds.remove(target);//从数组[111,112,115,888，222,333,444,555,666，999] 中移除888，第二次循环又从剩下的9个id中随机取出来，以此类推
        }
        //（3)填空题
        List tktIds = questionMapper.queryQuestionIdByTypeId(2L);//查询出所有的填空题的id
        for (int i = 0; i < tktNum; i++) {
            //该值介于[0,10)的区间
            Object target =  tktIds.get(new Random().nextInt(tktIds.size())); //888
            ids.add(target);
            tktIds.remove(target);
        }
        //(3)判断题
        List pdtIds = questionMapper.queryQuestionIdByTypeId(3L);
        for (int i = 0; i < pdtNum; i++) {
            //该值介于[0,10)的区间
            Object target =  pdtIds.get(new Random().nextInt(pdtIds.size())); //888
            ids.add(target);
            pdtIds.remove(target);
        }
        //(3)简答题
        List jdtIds = questionMapper.queryQuestionIdByTypeId(4L);
        for (int i = 0; i < jdtNum; i++) {
            //该值介于[0,10)的区间
            Object target =  jdtIds.get(new Random().nextInt(jdtIds.size())); //888
            ids.add(target);
            jdtIds.remove(target);
        }
        //（4）再保存数据(批量插入)
        // ids（即如上面的[111,112,115,888，222,333,444,555,666，999] ）
        // ids提交到后台 保存试卷对应的问题 保存进exam_paper_question表 一张试卷对应多个问题，所以批量保存、即插入到表里面

        //先删除试卷对应的问题
        paperMapper.deletePaperQuestion(paperId);
        //再插入试卷对应的问题表 完成批量插入   一张试卷对应多条问题[{paperId:1111,questionId:123},{paperId:1111,questionId:124}]
        List<Map> params = (List)ids.stream().map(id -> {//stream流循环取出每一个id（即问题id）
            Map mp1 = new HashMap();
            mp1.put("paperId", paperId);
            mp1.put("questionId", id);//questionId即实体表question的id属性
            return mp1;
        }).collect(Collectors.toList());
        //再保存数据(批量插入) 参数params传到 insertBatchPaperQuestion（）方法
        paperMapper.insertBatchPaperQuestion(params);

    }
}
