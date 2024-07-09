package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserShowResponseDTO {

  private String email;

  private String name;

  private String phone;

  private int age;

  private String address;

  private String profileImage;

  private Role role;

  public UserShowResponseDTO(User user) {
    this.email = user.getEmail();
    this.name = user.getName();
    this.phone = user.getPhone();
    this.age = user.getAge();
    this.address = user.getAddress();
    this.profileImage = user.getProfileImage();
    this.role = user.getRole();
  }
}
