package com.mz.auth.service.impl;

import com.mz.auth.mapper.StudentMapper;
import com.mz.auth.query.StudentQuery;
import com.mz.auth.service.StudentService;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    /**
     * 查询学生列表
     * @param studentQuery
     * @return
     */
    @Override
    public PageList listPage(StudentQuery studentQuery) {
        PageList pageList = new PageList();
        pageList.setTotal(studentMapper.queryTotal(studentQuery));
        pageList.setRows(studentMapper.queryData(studentQuery));
        return pageList;
    }
}
