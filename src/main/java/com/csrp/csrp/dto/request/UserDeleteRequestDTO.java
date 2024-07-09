package com.csrp.csrp.dto.request;

import jakarta.validation.constraints.Email;
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
public class UserDeleteRequestDTO {

  @Email
  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 5, max = 20)
  private String password;

}
