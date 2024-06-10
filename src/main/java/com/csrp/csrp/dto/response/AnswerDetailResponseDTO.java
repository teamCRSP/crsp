package com.csrp.csrp.dto.response;

import com.csrp.csrp.entity.Answer;
import com.csrp.csrp.type.AnswerState;
import com.csrp.csrp.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDetailResponseDTO {

  private String answer;

  private AnswerState answerState;

  private LocalDateTime createdAt;

  private String name;

  private String profileImage;

  private Role role;

  private String title;

  private String content;

  public AnswerDetailResponseDTO(Answer answer) {
    this.answer = answer.getAnswer();
    this.answerState = answer.getAnswerState();
    this.createdAt = answer.getCreatedAt();
    this.name = answer.getUser().getName();
    this.profileImage = answer.getUser().getProfileImage();
    this.role = answer.getUser().getRole();
    this.title = answer.getInquiry().getTitle();
    this.content = answer.getInquiry().getContent();
  }
}
