package com.csrp.csrp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Ticket extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ticket_id")
  private Long id;

  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;  // 종료 일자

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_detail_id")
  private ReservationDetail reservationDetail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
