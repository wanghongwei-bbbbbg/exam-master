package com.mz.auth.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) //toString打印父类属性
public class ScoreQuery extends BaseQuery {
    /**
     * 试卷名
     */
    private String name;
}
