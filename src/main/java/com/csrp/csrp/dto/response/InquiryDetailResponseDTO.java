package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryDetailResponseDTO {

  private String title;

  private String content;

  private String name;

  private String profileImage;

  private String concertImage;

  private LocalDateTime createdAt;

  public InquiryDetailResponseDTO(Inquiry inquiry) {
    this.title = inquiry.getTitle();
    this.content = inquiry.getContent();
    this.name = inquiry.getUser().getName();
    this.profileImage = inquiry.getUser().getProfileImage();
    this.concertImage = inquiry.getConcertInfo().getConcertImage();
    this.createdAt = inquiry.getCreatedAt();
  }



}
