package com.mz.auth.service;

import com.mz.auth.entity.DicTypeData;

import java.util.List;

public interface DicService {
    /**
     * 查询所有试卷等级
     */
    List<DicTypeData> findLevels();

}
