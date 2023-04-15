package com.mz.auth.web.controller;

import com.mz.auth.entity.User;
import com.mz.auth.query.UserQuery;
import com.mz.auth.service.TeacherService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/teacher")
@Api(tags = "在线考试系统-教师管理端口")
@Slf4j
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    /**
     * 注册老师
     */
    @RequestMapping("/regTeacher")
    @ResponseBody
    public MzResult regTeacher(User user) {
        log.info("注册老师，user信息={}", user);
        try {
            Long userId = teacherService.addTeacher(user);
            return MzResult.ok().put("userid", userId);
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 跳转到老师列表页面（前端右边显示）
     */
    @GetMapping("/index")
    public String index() {
        log.info("跳转到老师列表页面");
        return "views/teacher/teacher_list";
    }

    /**
     * 查询老师列表数据(可模糊查询)，分页由前端做到。
     */
    @GetMapping("/listpage")
    @ResponseBody
    public PageList listPage(UserQuery userQuery) {
        log.info("查询老师数据列表,userQuery={}", userQuery);
        userQuery.setType(2);//2代表老师
        return teacherService.listPage(userQuery);
    }

}
