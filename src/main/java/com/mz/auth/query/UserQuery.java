package com.mz.auth.query;

import lombok.Data;
import lombok.ToString;

/**
 * @description: UserQuery 用来接收前台传递参数
 * @author:soulcoder 自由如风
 * @email: 3393857689@qq.com
 * @date: created by 2021/8/21 22:42
 */
@Data
@ToString(callSuper = true) //toString打印父类属性
public class UserQuery extends BaseQuery{
    /**
     * username 用户名
     */
    private String username;
    /**
     * email 邮件
     */
    private String email;
    /**
     * type 用户类型 type=1表示管理员
     * type=2表示老师
     */
    private Integer type;

}
