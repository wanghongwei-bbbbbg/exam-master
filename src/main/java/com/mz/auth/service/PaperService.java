package com.mz.auth.service;

import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperGengerateVO;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.entity.TypeTotalVO;
import com.mz.auth.query.PaperQuery;
import com.mz.auth.util.PageList;

import java.util.List;
import java.util.Map;

public interface PaperService {
    /**
     * 分页查询数据
     */
    PageList listPage(PaperQuery paperQuery);

    /**
     * 新增试卷
     */
    void addPaper(Paper paper);

    /**
     * 根据删除试卷及组题记录
     */
    void deletePaper(Long id);

    /**
     * 根据id修改试卷部分信息
     */
    void editSavePaper(Paper paper);

    /**
     * 查询所有试卷数据,前端组成树状图
     * @return
     */
    List<Paper> queryPaper();

    /**
     * 试题组卷：根据试卷id查询对应的问题记录
     * @param paperId
     * @return
     */
    List<PaperQuestion> queryQuestionByPaperId(Long paperId);

    /**
     * 手动组卷
     * @param paperQuestion
     */
    void diyPaperQuestion(PaperQuestion paperQuestion);

    /**
     * 预览试卷方法
     * @param paperId
     * @return
     */
    PaperGengerateVO previewPaper(Long paperId);

    /**
     * 查询题类型的总数
     * @return
     */
    List<TypeTotalVO> queryTypeTotal();

    /**
     * 随机组卷
     * @param mp
     */
    void randomPaperQuestion(Map mp);

}
