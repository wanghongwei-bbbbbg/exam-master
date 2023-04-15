package com.mz.auth.service.impl;

import com.mz.auth.mapper.ScoreMapper;
import com.mz.auth.query.ScoreQuery;
import com.mz.auth.service.ScoreService;
import com.mz.auth.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    ScoreMapper scoreMapper;

    @Override
    public PageList listPage(ScoreQuery scoreQuery) {
        PageList pageList = new PageList();
        //TODO 功能未实现
        pageList.setTotal(0L);
        pageList.setRows(new ArrayList());
        return pageList;
    }
}
