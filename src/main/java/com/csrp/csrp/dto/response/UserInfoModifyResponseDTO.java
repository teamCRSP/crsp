package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoModifyResponseDTO {

  private String name;

  private String phone;

  private String address;

  private String profileImage;

  public UserInfoModifyResponseDTO(User user) {
    this.name = user.getName();
    this.phone = user.getPhone();
    this.address = user.getAddress();
    this.profileImage = user.getProfileImage();
  }

}
