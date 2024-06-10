package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.Answer;
import com.csrp.csrp.entity.Inquiry;
import com.csrp.csrp.entity.User;
import com.csrp.csrp.type.AnswerState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRegisterRequestDTO {

  @NotNull
  private Long inquiryId;

  private String answer;

  public Answer toEntity(AnswerRegisterRequestDTO answerRegisterRequestDTO, User user, Inquiry inquiry) {
    return Answer.builder()
        .answer(answerRegisterRequestDTO.getAnswer())
        .answerState(AnswerState.COMPLETED)
        .user(user)
        .inquiry(inquiry)
        .build();
  }
}
