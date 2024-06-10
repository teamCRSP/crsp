package com.csrp.csrp.entity;

import com.csrp.csrp.type.AnswerState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Answer extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "answer_id")
  private Long id;

  @Column(name = "answer")
  private String answer;

  @Column(name = "answer_state", nullable = false)
  @Enumerated(EnumType.STRING)
  @ColumnDefault("'PENDING'")
  private AnswerState answerState;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inquiry_id")
  private Inquiry inquiry;
}
