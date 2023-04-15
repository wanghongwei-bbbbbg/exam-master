package com.mz.auth.entity;

import lombok.Data;

@Data
public class Score {
    private Long id;
    private Long stuId;
    private Long paperId;
    private String totalScore;
}
