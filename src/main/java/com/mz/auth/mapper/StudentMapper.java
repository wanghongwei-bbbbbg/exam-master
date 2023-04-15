package com.mz.auth.mapper;

import com.mz.auth.entity.Student;
import com.mz.auth.query.StudentQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 查询总条数
     * @param studentQuery
     * @return
     */
    //@Select("select count(*) from t_student")
    Long queryTotal(StudentQuery studentQuery);

    /**
     * 查询总数据
     * @param studentQuery
     * @return
     */
    //@Select("select * from t_student")
    List<Student> queryData(StudentQuery studentQuery);


}
