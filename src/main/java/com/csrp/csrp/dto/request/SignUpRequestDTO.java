package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.Role;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequestDTO {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Size(min = 5, max = 20)
  private String password;

  @NotBlank
  @Size(min = 2, max = 10)
  private String name;

  @NotBlank
  @Size(min = 11, max = 11)
  private String phone;

  @NotNull
  @Min(value = 1)
  private Integer age;

  @NotBlank
  private String address;

  private Role role;

  // 유저 프로필 이미지 엔터티에 주입

  public User toEntity(String profileImage, String encodedPassword) {
    return User.builder()
        .email(this.email)
        .password(encodedPassword)
        .name(this.name)
        .phone(this.phone)
        .age(this.age)
        .address(this.address)
        .role(this.role)
        .profileImage(profileImage)
        .build();
  }
}
