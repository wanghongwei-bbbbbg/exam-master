package com.mz.auth.web.controller;

import com.mz.auth.query.StudentQuery;
import com.mz.auth.service.StudentService;
import com.mz.auth.util.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/index")
    public String index(){
        log.info("跳转到学生列表页面");
        return "views/student/student_list";
    }

    @GetMapping("/listpage")
    @ResponseBody
    public PageList listPage(StudentQuery studentQuery){
        log.info("查询学生列表数据,{}",studentQuery);
        PageList pageList = studentService.listPage(studentQuery);
        return pageList;
    }


}
