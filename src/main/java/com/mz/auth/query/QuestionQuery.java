package com.mz.auth.query;

import lombok.Data;
import lombok.ToString;

/**
 * @description: QuestionQuery
 * 用来接收前台传递参数 questionTitle
 */
@Data
@ToString(callSuper = true) //toString打印父类属性
public class QuestionQuery extends BaseQuery{
    /**
     * @Data 标签对于继承的属性不会toString
     * 需要打印的话加上 @ToString(callSuper = true) 就能打印父类属性
     * 或者手动重写toString方法，调用父类的toString+“自己的toString具体内容”。
     */

    /**
     * 问题的名称
     */
    private String questionTitle;



}
