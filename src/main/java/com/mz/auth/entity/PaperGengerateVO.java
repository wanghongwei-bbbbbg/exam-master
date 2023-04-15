package com.mz.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: PaperGengerateVO 生成预览试卷 对象
 * 试卷信息  试题信息
 */
@Data
public class PaperGengerateVO {

    private Long id;//试卷id

    private String name;//试卷名称

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //展示页面
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss") //后台接收时间
    private Date startTime;//测试开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //展示页面
    @DateTimeFormat(pattern =  "yyyy-MM-dd HH:mm:ss") //后台接收时间
    private Date endTime;//测试结束时间

    /**
     * Question实体表 对象
     */
    private List<Question> questions = new ArrayList<>();

}