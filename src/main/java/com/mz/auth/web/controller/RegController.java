package com.mz.auth.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 响应注册相关请求
 */
@Controller
@Slf4j
public class RegController {

    /**
     * 跳转到注册页面
     * /gotoReg是前端访问后端的请求路径
     * views/reg是服务器内部静态资源的转发
     * @return
     */
    @RequestMapping("/gotoReg")
    public String gotoReg(){
        log.info("跳转到注册页面");
        return "views/reg";
    }

}
