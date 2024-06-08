package com.csrp.csrp.form;

import com.csrp.csrp.type.ConcertType;
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
public class ConcertForm {

  @NotNull(message = "콘서트 제목 작성")
  private String title;

  @NotNull(message = "콘서트 상세 설명 작성")
  private String description;

  @NotNull(message = "아티스트 작성")
  private String artist;

  @NotNull(message = "콘서트 가격 작성")
  private Integer amount;

  @NotNull(message = "콘서트 개최날 작성")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  private LocalDateTime date;

  @NotNull(message = "콘서트 개최 위치 작성")
  private String location;

  @NotNull(message = "콘서트 타입(종류) 작성")
  private ConcertType concertType;

  //@NotNull(message = "S석 갯수 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatS;

  //@NotNull(message = "S석 가격 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatSPrice;

  //@NotNull(message = "A석 갯수 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatA;

  //@NotNull(message = "A석 가격 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatAPrice;

  //@NotNull(message = "B석 갯수 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatB;

  //@NotNull(message = "B석 가격 작성")
  @PositiveOrZero(message = "금액은 0보다 크거나 같아야 합니다")
  private Integer seatBPrice;


}
