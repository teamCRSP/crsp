package com.csrp.csrp.entity;

import com.csrp.csrp.type.PaymentStatus;
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
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long id;

  @Column(name = "amount", nullable = false)
  private int amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "'PENDING'")
  private PaymentStatus paymentStatus;

  @Column(name = "payment_date", nullable = false)
  private LocalDateTime paymentDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_history_id")
  private ReservationHistory reservationHistory;
}
