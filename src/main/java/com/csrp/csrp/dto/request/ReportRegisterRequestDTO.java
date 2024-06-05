package com.csrp.csrp.dto.request;

import com.csrp.csrp.entity.ReportAccepted;
import com.csrp.csrp.entity.Review;
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
public class ReportRegisterRequestDTO {

  @NotNull
  private Long reviewId;

  public ReportAccepted toEntity(User user, int warningCount) {
    return ReportAccepted.builder()
        .warningCount(warningCount)
        .user(user)
        .build();
  }
  public ReportAccepted toModifyEntity(User user, int warningCount, ReportAccepted reportAccepted) {
    return ReportAccepted.builder()
        .id(reportAccepted.getId())
        .warningCount(warningCount)
        .user(user)
        .build();
  }
}
