package com.mz.auth.web.controller;

import com.mz.auth.query.ScoreQuery;
import com.mz.auth.service.ScoreService;
import com.mz.auth.util.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/score")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @GetMapping("/index")
    public String index(){
        log.info("跳转到成绩列表页面");
        return "views/score/score_list";
    }

    @GetMapping("/listpage")
    @ResponseBody
    public PageList listPage(ScoreQuery scoreQuery){
        log.warn("[功能未实现]查询成绩列表数据,{}",scoreQuery);
        return scoreService.listPage(scoreQuery);
    }
}
