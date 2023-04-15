package com.mz.auth.service;

import com.mz.auth.query.StudentQuery;
import com.mz.auth.util.PageList;

public interface StudentService {
    /**
     * 查询学生列表
     * @param studentQuery
     * @return
     */
    PageList listPage(StudentQuery studentQuery);
}
