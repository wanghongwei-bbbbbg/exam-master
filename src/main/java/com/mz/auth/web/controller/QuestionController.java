package com.mz.auth.web.controller;

import com.mz.auth.entity.Question;
import com.mz.auth.query.QuestionQuery;
import com.mz.auth.service.QuestionService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    /**
     * 跳转题库列表页
     */
    @RequestMapping("/index")
    public String index() {
        log.info("跳转到题目列表页面");
        return "views/question/question_list";
    }

    /**
     * 分页查询数据
     * @param questionQuery
     * @return
     */
    @RequestMapping("/listpage")
    @ResponseBody
    public PageList listPage(QuestionQuery questionQuery) {
        log.info("查询题目数据列表,{}", questionQuery);
        return questionService.listPage(questionQuery);
    }

    /**
     * 跳转到添加题目页面
     * @return
     */
    @RequestMapping("/gotoAddQuestion")
    public String gotoAddQuestion() {
        log.info("跳转到添加题目页面");
        return "views/question/question_add";
    }


    /**
     * 添加题目
     * @param question
     * @return
     */
    @PostMapping("/addQuestion")
    @PreAuthorize("hasRole('管理员')" +
            "|| hasRole('老师权限')")
    @ResponseBody
    public MzResult addQuestion(@RequestBody Question question) {
        log.info("添加题目{}", question);
        try {
            questionService.addQuestion(question);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 跳转题库的修改页面
     * @return
     */
    @RequestMapping("/gotoEditQuestion/{id}")
    public String index(@PathVariable("id") Long id, Model model) {
        log.info("跳转到修改题目页面,同时保存id={}至model里", id);
        //Long id, Model model即把id存进model里面
        model.addAttribute("qid", id);
        return "views/question/question_edit";
    }

    /**
     * 根据问题id查询问题，实现数据回显功能
     * @param qid
     * @return
     */
    @PostMapping("/queryQuestionByQid")
    @ResponseBody
    public Question queryQuestionByQid(Long qid) {
        log.info("根据id={}查询题目数据,做数据回显", qid);
        return questionService.queryQuestionByQid(qid);
    }

    /**
     * 修改题目
     * @param question
     * @return
     */
    @PostMapping("/editQuestion")
    @ResponseBody
    public MzResult editQuestion(@RequestBody Question question) {
        log.info("修改题目,{}", question);
        try {
            questionService.editQuestion(question);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 删除问题方法
     * @param id
     * @return
     */
    @GetMapping("/deleteQuestion")
    @ResponseBody
    public MzResult deleteQuestion(Long id) {
        log.info("删除题目,id={}", id);
        try {
            questionService.deleteQuestion(id);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }
}
