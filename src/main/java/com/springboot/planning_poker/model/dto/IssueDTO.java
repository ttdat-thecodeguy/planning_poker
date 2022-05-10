package com.springboot.planning_poker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class IssueDTO {
    private String id;
    private String name;
    private String storyPoint;
}
