package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "history") @AllArgsConstructor @NoArgsConstructor @Data

/**
 id
 date
 issue( 1-1 )
 result
 per_agree
 duration
 user_voted
 total_user
 **/

public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Issue issue;
    // điểm trung bình 1 vote
    private double result;
    // tỉ lệ chấp thuận
    private double per_agree;
    private int users_voted;
    private int users_total;
}
