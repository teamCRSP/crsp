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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ReservationDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_detail_id")
  private Long id;

  @Column(name = "concert_seat", nullable = false)
  private String concertSeat;  // 콘서트 좌석

  @Column(name = "ticket_count", nullable = false)
  private int ticketCount;  // 티켓 수량

  @Column(name = "concert_date")
  private LocalDateTime concertDate; // 콘서트 날짜

  @Column(name = "ticketing_date", nullable = false)
  private LocalDateTime ticketingDate;  // 예매 일시

  @Column(name = "concert_seat_price")
  private int concertSeatPrice; // 콘서트 좌석 가격

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_loc_info_id")
  private ConcertLocInfo concertLocInfo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_history_id")
  private ReservationHistory reservationHistory;

}

