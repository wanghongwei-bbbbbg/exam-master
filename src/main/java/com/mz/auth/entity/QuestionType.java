package com.mz.auth.entity;

import lombok.Data;

/**
 * @description: QuestionType问题的类型
 */
@Data
public class QuestionType {
    //主键id
    private Long id;
    //题的类型的名称
    private String name;
    //类型的描述
    private String desc;
    //类型的编号
    private String typenum;

}
