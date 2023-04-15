package com.mz.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Student {
    private Long id;
    private String username;
    private String password;
    private String tel;
    private String email;
    private String stuNum;

    /**
     * 处理时间格式,(大写H)24小时,东八区
     * 试卷创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String nickName;

}
