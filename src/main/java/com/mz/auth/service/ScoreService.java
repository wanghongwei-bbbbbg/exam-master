package com.mz.auth.service;

import com.mz.auth.query.ScoreQuery;
import com.mz.auth.util.PageList;

public interface ScoreService {
    PageList listPage(ScoreQuery scoreQuery);
}
