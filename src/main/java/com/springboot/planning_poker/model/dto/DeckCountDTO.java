package com.springboot.planning_poker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @Data @NoArgsConstructor
public class DeckCountDTO {
    private String item;
    private long count;

}
