package com.mz.auth.mapper;

import com.mz.auth.entity.Paper;
import com.mz.auth.entity.PaperGengerateVO;
import com.mz.auth.entity.PaperQuestion;
import com.mz.auth.entity.TypeTotalVO;
import com.mz.auth.query.PaperQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaperMapper {
    /**
     * 查询总的条数
     * @param paperQuery
     * @return
     */
    Long queryTotal(PaperQuery paperQuery);

    /**
     * 查询当前页的数据
     * @param paperQuery
     * @return
     */
    List<Paper> queryData(PaperQuery paperQuery);


    /**
     * 保存试卷
     * @param paper
     */
    @Insert("insert into exam_paper(name, levelid, status, startTime, endTime, createTime)" +
            "values(#{name},#{levelid}, #{status}, #{startTime}, #{endTime}, #{createTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void addPaper(Paper paper);


    /**
     * 根据id删除试卷
     * @param id
     */
    @Delete("delete from exam_paper where id= #{id}")
    void deletePaper(Long id);

    /**
     * 根据id删除试卷对应的组题记录
     */
    @Delete("delete from exam_paper_question where id= #{id}")
    void deletePaperQuestion(Long id);

    /**
     * 根据id修改试卷,只能修改部分信息
     * @param paper
     */
    @Update("update exam_paper set name=#{name},levelid=#{levelid},startTime=#{startTime},endTime=#{endTime} " +
            "where id =#{id}")
    void editSavePaper(Paper paper);

    /**
     * 查询所有试卷信息
     * @return
     */
    @Select("select * from exam_paper")
    List<Paper> queryPaper();

    /**
     * 试题组卷
     * @param paperId
     * @return
     */
    @Select("select * from exam_paper_question where paperId=#{paperId}")
    List<PaperQuestion> queryQuestionByPaperId(Long paperId);

    /**
     * 批量插入试卷对应的 试题表 里面 即试卷问题表
     * @param params
     */
    @Insert("<script>insert into exam_paper_question(paperId,questionId) " +
            "values" +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.paperId},#{item.questionId})"+
            "</foreach>"+
            "</script>")
    void insertBatchPaperQuestion(List<Map> params);

    /**
     * 预览试卷方法   用xml方式查询，因为sql较复杂，不用注解
     * @param paperId
     * @return
     */
    PaperGengerateVO previewPaper(Long paperId);

    /**
     * 查询题型的总数  group by根据 q_typeid来查询totalNum总数量  如选择题一共5条
     * @return
     */
    @Select("select q_typeid,count(*) totalNum\n" +
            "from exam_questionbank\n" +
            "group by q_typeid")
    List<TypeTotalVO> queryTypeTotal();

}
