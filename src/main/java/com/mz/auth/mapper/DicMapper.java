package com.mz.auth.mapper;

import com.mz.auth.entity.DicTypeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DicMapper {
    /**
     * 查询所有试卷难度等级
     * @return
     */
    @Select("select * from t_dictype_data where typeid=1")
    List<DicTypeData> findLevels();

}
