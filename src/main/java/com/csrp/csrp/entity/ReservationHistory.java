package com.csrp.csrp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ReservationHistory extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_history_id")
  private Long id;

  @Column(name = "seat_info", nullable = false)
  private String seatInfo;  // 좌석 정보

  @Column(name = "count", nullable = false)
  private int count;  // 개수

  @Column(name = "concert_date")
  private String concertDate; // 콘서트 날짜

  @Column(name = "concert_location", nullable = false)
  private String concertLocation; // 콘서트 장소

  @Column(name = "amount", nullable = false)
  private int amount; // 총 가격

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;

//  @OneToMany(mappedBy = "reservationHistory")
//  private List<Ticket> tickets = new ArrayList<Ticket>();
}
