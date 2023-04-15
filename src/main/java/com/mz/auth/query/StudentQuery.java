package com.mz.auth.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) //toString打印父类属性
public class StudentQuery extends BaseQuery {
    /**
     * 学生名称
     */
    private String username;
}
