package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInResponseDTO {
  private Long id;

  private String email;

  private Role role;

  private String phone;

  private String name;

  private String token;

  public SignInResponseDTO(String token, User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.role = user. getRole();
    this.phone = user.getPhone();
    this.token = token;
  }
}
