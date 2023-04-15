package com.mz.auth.entity;

import lombok.Data;

/**
 * 试卷难度等级
 */
@Data
public class DicTypeData {
    private Long id;

    private String name;

    private Long typeid;//关联类型表id 1代表试卷难度,2代表试卷分类
}
