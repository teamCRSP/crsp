package com.csrp.csrp.entity;

import com.csrp.csrp.type.ReviewStopStatus;
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

  @Column(name = "review_warning_count", nullable = false)
  private int reviewWarningCount = 0;

  @Column(name = "review_stop_status", nullable = false)
  @Enumerated(EnumType.STRING)
  @ColumnDefault("'NO'")
  private ReviewStopStatus reviewStopStatus;  // 정지 이력

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;


}
