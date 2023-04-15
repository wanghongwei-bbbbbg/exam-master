package com.mz.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Paper {
    /**
     * 试卷id
     */
    private Long id;

    /**
     * 试卷名
     */
    private String name;

    /**
     * 难度等级
     */
    private Long levelid;

    /**
     * 试卷状态 0为无效 1为有效
     */
    private Integer status;

    /**
     * JsonFormat处理数据库存入时间格式,(大写H)24小时,东八区
     * DateTimeFormat处理前端传来的时间格式
     * 开始时间
     * 如果用的是MP,则用LocalDateTime类型,MP内部应该处理好了时间格式.
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 处理时间格式,(大写H)24小时,东八区
     * 结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 处理时间格式,(大写H)24小时,东八区
     * 试卷创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
