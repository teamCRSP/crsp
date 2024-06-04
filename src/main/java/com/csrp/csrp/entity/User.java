package com.csrp.csrp.entity;

import com.csrp.csrp.type.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
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

}
