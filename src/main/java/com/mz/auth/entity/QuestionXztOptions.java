package com.mz.auth.entity;

import lombok.Data;

/**
 * @description: QuestionXztOptions 选择题的选项数据 实体
 */
@Data
public class QuestionXztOptions {
    //主键id
    private Long id;
    //选择题A选项
    private String optionA;
    //选择题B选项
    private String optionB;
    //选择题c选项
    private String optionC;
    //选择题d选项
    private String optionD;
    /**
     * 问题编号 id
     */
    private Long questionId;
}
