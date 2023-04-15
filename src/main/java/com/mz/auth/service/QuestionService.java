package com.mz.auth.service;

import com.mz.auth.entity.Question;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.util.PageList;

public interface QuestionService {

    /**
     * 分页查询数据
     */
    PageList listPage(QuestionQuery questionQuery);

    /**
     * 添加题目
     * @param question
     */
    void addQuestion(Question question);

    /**
     * 根据问题id 查询问题
     * @param qid
     * @return
     */
    Question queryQuestionByQid(Long qid);

    /**
     * 修改题目
     * @param question
     */
    void editQuestion(Question question);

    /**
     * 删除题目
     * @param id
     */
    void deleteQuestion(Long id);
}
