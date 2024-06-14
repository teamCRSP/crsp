package com.csrp.csrp.form;

import com.csrp.csrp.type.ConcertType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcertForm {

  @NotNull(message = "콘서트 제목 작성")
  private String title;

  @NotNull(message = "콘서트 상세 설명 작성")
  private String description;

  @NotNull(message = "아티스트 작성")
  private String artist;

  @NotNull(message = "콘서트 개최 위치 작성")
  private List<String> location = new ArrayList<>();

  @NotNull(message = "콘서트 타입(종류) 작성")
  private ConcertType concertType;

  @NotNull(message = "콘서트 시작일 작성")
  private LocalDateTime startDate;

  @NotNull(message = "콘서트 종료일 작성")
  private LocalDateTime endDate;

  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatSPrice;


  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatAPrice;

  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatBPrice;


}
