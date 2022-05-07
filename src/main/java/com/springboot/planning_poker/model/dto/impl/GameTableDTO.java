package com.springboot.planning_poker.model.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class GameTableDTO {
    private String id;
    private IssueDTO issueActive;
}
