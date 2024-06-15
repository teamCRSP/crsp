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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class ReservationHistory extends BaseTime{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reservation_history_id")
  private Long id;

  @Column(name = "amount", nullable = false)
  private int amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;


  @OneToMany(mappedBy = "reservationHistory", cascade = CascadeType.ALL)
  private List<ReservationDetail> reservationDetails = new ArrayList<ReservationDetail>();

  @OneToOne(mappedBy = "reservationHistory", cascade = CascadeType.PERSIST)
  private PaymentHistory paymentHistory = new PaymentHistory();

  @OneToMany(mappedBy = "reservationHistory", cascade = CascadeType.PERSIST)
  private List<Ticket> tickets = new ArrayList<Ticket>();



}
