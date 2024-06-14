package com.csrp.csrp.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EachConcertUpdateForm {

  @NotNull(message = "콘서트 개최일시 작성")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime startDate;

  @NotNull(message = "콘서트 종료일시 작성")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime endDate;

  @PositiveOrZero(message = "좌석수는 0보다 크거나 같아야 합니다")
  private Integer seatS;

  @PositiveOrZero(message = "좌석수는 0보다 크거나 같아야 합니다")
  private Integer seatA;

  @PositiveOrZero(message = "좌석수는 0보다 크거나 같아야 합니다")
  private Integer seatB;
}
