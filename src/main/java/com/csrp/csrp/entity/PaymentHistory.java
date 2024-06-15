package com.csrp.csrp.entity;

import com.csrp.csrp.type.PaymentStatus;
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
public class PaymentHistory {

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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_history_id")
  private ReservationHistory reservationHistory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public static PaymentHistory toEntity(User user, ReservationHistory reservationHistory) {
    return PaymentHistory.builder()
        .amount(reservationHistory.getAmount())
        .paymentDate(LocalDateTime.now())
        .paymentStatus(PaymentStatus.COMPLETED)
        .user(user)
        .reservationHistory(reservationHistory)
        .build();
  }

}
