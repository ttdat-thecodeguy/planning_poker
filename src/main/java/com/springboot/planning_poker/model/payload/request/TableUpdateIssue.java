package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TableUpdateIssue {
    private String tableId;
    private String issueId;
    private boolean isAdd;
}
