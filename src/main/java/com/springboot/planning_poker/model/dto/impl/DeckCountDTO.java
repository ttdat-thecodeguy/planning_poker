package com.springboot.planning_poker.model.dto.impl;

import com.springboot.planning_poker.model.dto.IDeckCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @Data @NoArgsConstructor
public class DeckCountDTO {
    private String item;
    private long count;
    public DeckCountDTO(IDeckCount deck){
        this.item = deck.getItem();
        this.count = deck.getCount();
    }
}
