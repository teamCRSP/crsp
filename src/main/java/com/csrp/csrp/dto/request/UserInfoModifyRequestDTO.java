package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoModifyRequestDTO {

  @NotBlank
  @Size(min = 2, max = 10)
  private String name;

  @NotBlank
  @Size(min = 11, max = 11)
  private String phone;

  @NotBlank
  private String address;

  public User modifyUser(User user, UserInfoModifyRequestDTO userInfoModifyRequestDTO, String profileImagePath) {
    return User.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .name(userInfoModifyRequestDTO.name)
        .age(user.getAge())
        .phone(userInfoModifyRequestDTO.phone)
        .address(userInfoModifyRequestDTO.address)
        .role(user.getRole())
        .profileImage(profileImagePath)
        .build();
  }
}
