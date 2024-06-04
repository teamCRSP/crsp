package com.csrp.csrp.token;

import com.csrp.csrp.type.Role;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenUserInfo {
  private Long id;

  private String email;

  private Role role;

  private String phone;

  private String name;
}
