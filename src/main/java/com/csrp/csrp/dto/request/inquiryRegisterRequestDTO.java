package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.entity.Inquiry;
import com.csrp.csrp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class inquiryRegisterRequestDTO {
  @NotNull
  private ConcertInfo concertInfo;

  @NotBlank
  @Size(min = 1, max = 1000)
  private String title;

  @NotBlank
  @Size(min = 1, max = 100)
  private String content;

  public Inquiry toEntity(inquiryRegisterRequestDTO inquiryRegisterRequestDTO, User user, ConcertInfo concertInfo) {
    return Inquiry.builder()
        .title(inquiryRegisterRequestDTO.title)
        .content(inquiryRegisterRequestDTO.content)
        .user(user)
        .concertInfo(concertInfo)
        .build();
  }
}
