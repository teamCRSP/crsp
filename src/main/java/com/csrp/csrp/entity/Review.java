package com.csrp.csrp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Review extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "review_id")
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "rating", nullable = false)
  private int rating;

  @Column(name = "sanction", nullable = false)
  private boolean sanction = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "concert_id")
//  private ConcertInfo concertInfo;


}
