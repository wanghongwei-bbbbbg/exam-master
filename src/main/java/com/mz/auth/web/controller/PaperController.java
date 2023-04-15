package com.mz.auth.web.controller;

import com.mz.auth.entity.*;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.service.DicService;
import com.mz.auth.service.PaperService;
import com.mz.auth.util.MzResult;
import com.mz.auth.util.PageList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    @Autowired
    private DicService dicService;

    /**
     * 跳转到试卷列表页面,同时查询试卷难度等级
     */
    @GetMapping("/index")
    public String index(Model model) {
        /*Model,利用它可以把数据带给前端https://blog.csdn.net/xiaokanshijie/article/details/87949874*/
        log.info("查询试卷难度等级,跳转到试卷列表页面");
        //查询试卷难度等级,前端才能选择
        List<DicTypeData> levels = dicService.findLevels();
        model.addAttribute("levels", levels);

        return "views/paper/paper_list";
    }

    /**
     * 试卷列表
     */
    @RequestMapping("/listpage")
    @ResponseBody
    public PageList listPage(PaperQuery paperQuery) {
        log.info("查询试卷数据列表,paperQuery={}", paperQuery);
        return paperService.listPage(paperQuery);
    }

    /**
     * 新增试卷
     * 要先做试卷等级查询，不然前端选不了等级传过来的是String类型的[-请选择-]会装不了
     */
    @RequestMapping("/savePaper")
    @ResponseBody
    public MzResult savePaper(Paper paper) {
        log.info("新增试卷，paper={}", paper);
        try {
            paperService.addPaper(paper);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 根据id删除试卷及组题记录
     */
    @RequestMapping("deletePaper")
    @ResponseBody
    public MzResult deletePaper(Long id) {
        log.info("根据id删除试卷及组题记录,id={}", id);
        try {
            paperService.deletePaper(id);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 根据id修改试卷部分信息
     */
    @RequestMapping("/editSavePaper")
    @ResponseBody
    public MzResult editSavePaper(Paper paper){
        log.info("根据id修改试卷信息,paper={}",paper);
        try{
            paperService.editSavePaper(paper);
            return MzResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 跳转 试卷组题页面
     * @return
     */
    @GetMapping("/appendQuestion")
    public String appendQuestion(){
        return "views/paper/paper_question";
    }

    /**
     * 试卷列表
     */
    @RequestMapping("/queryPaper")
    @ResponseBody
    public List<Paper> queryPaper() {
        log.info("查询所有试卷，组成树状图");
        List<Paper> papers = paperService.queryPaper();
        return papers;
    }

    /**
     * 根据试卷id查询所有的问题
     * @return   List<PaperQuestion>
     */
    @PostMapping("/queryQuestionByPaperId")
    @ResponseBody
    public List<PaperQuestion> queryQuestionByPaperId(@RequestBody PaperQuestion paperQuestion) {
        return paperService.queryQuestionByPaperId(paperQuestion.getPaperId());
    }

    /**
     * 手动组卷
     * @param paperQuestion
     * @return
     */
    @PostMapping("/diyPaperQuestion")
    @ResponseBody
    //实体表 的paperQuestion对象
    public MzResult diyPaperQuestion(@RequestBody PaperQuestion paperQuestion) {
        log.info("手动组卷，{}", paperQuestion);
        try {
            paperService.diyPaperQuestion(paperQuestion);
            return MzResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());
        }
    }

    /**
     * 根据id查询预览试卷信息 跳转页面进行预览试卷
     * @param paperId
     * @return
     */
    @RequestMapping("/previewPaper/{paperId}")
    public String previewPaper(@PathVariable("paperId") Long paperId, Model model){
        log.info("根据id={}查询预览试卷信息 跳转页面进行预览试卷",paperId);
        //根据试卷id 查询出试卷的信息
        PaperGengerateVO paperGengerateVO= paperService.previewPaper(paperId);
        //model来存储查询到的信息，paperGengerateVO对象，然后前台再把信息取出来
        model.addAttribute("paperGengerateVO",paperGengerateVO);
        //跳转到预览页面
        return "views/paper/paper_preview";
    }

    /**
     * 查询题型的总数  List<TypeTotalVO>
     * queryTypeTotal
     */
    @PostMapping("/queryTypeTotal")
    @ResponseBody  //返回的json格式的数据
    public List<TypeTotalVO> queryTypeTotal(){
        log.info("查询题型的总数");
        return paperService.queryTypeTotal();
    }

    /**
     * 随机组卷的方法
     */
    @PostMapping("/randomPaperQuestion")
    @ResponseBody
    public MzResult randomPaperQuestion(@RequestBody Map mp){
        try {
            paperService.randomPaperQuestion(mp);
            return MzResult.ok();//返回成功
        } catch (Exception e) {
            e.printStackTrace();
            return MzResult.error(e.getMessage());//返回失败，打印出失败消息
        }
    }


}
