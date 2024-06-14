package com.csrp.csrp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @Column(name = "concert_date", nullable = false)
  private LocalDateTime concertDate;  // 콘서트 날짜

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_history_id")
  private ReservationHistory reservationHistory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
