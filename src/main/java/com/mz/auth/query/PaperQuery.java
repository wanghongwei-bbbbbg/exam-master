package com.mz.auth.query;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true) //toString打印父类属性
public class PaperQuery extends BaseQuery {
    /**
     * 试卷id
     */
    private Long id;
    /**
     * 试卷名称
     */
    private String name;

}
