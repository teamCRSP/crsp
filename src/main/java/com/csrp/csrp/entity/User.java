package com.csrp.csrp.entity;

import com.csrp.csrp.type.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class User extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Email
  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "phone", nullable = false)
  private String phone;

  @Column(name = "age", nullable = false)
  private int age;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "profile_image", nullable = true)
  private String profileImage;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  @ColumnDefault("'CUSTOMER'")
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<ReportAccepted> reportAccepted = new ArrayList<ReportAccepted>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Love> loves = new ArrayList<Love>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Answer> answers = new ArrayList<Answer>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Inquiry> inquiries = new ArrayList<Inquiry>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<ConcertInfo> concertInfos = new ArrayList<ConcertInfo>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<ReservationHistory> reservationHistories = new ArrayList<ReservationHistory>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Ticket> tickets = new ArrayList<Ticket>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<PaymentHistory> paymentHistories = new ArrayList<PaymentHistory>();
}
