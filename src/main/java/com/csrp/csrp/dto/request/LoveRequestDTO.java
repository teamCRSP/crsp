package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Love;
import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoveRequestDTO {

  @NotNull
  private Long concertInfoId;

  public Love toEntity(User user, ConcertInfo concertInfo) {
    return Love.builder()
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }
}
