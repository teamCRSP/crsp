package com.csrp.csrp.form;

import com.csrp.csrp.entity.ConcertInfo;
import com.csrp.csrp.type.ConcertType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConcertUpdateForm {

    @NotNull
    private Long id;

    @NotNull(message = "콘서트 제목 작성")
    private String title;

    @NotNull(message = "콘서트 상세 설명 작성")
    private String description;

    @NotNull(message = "아티스트 작성")
    private String artist;

    @NotNull(message = "콘서트 가격 작성")
    private Integer amount;

    @NotNull(message = "콘서트 개최일시 작성")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @NotNull(message = "콘서트 종료일시 작성")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @NotNull(message = "콘서트 개최 위치 작성")
    private List<String> location = new ArrayList<>();

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

    public ConcertInfo from(ConcertUpdateForm form, String concertImagePath){

        return ConcertInfo.builder()
                .id(form.getId())
                .title(form.getTitle())
                .artist(form.getArtist())
                .description(form.getDescription())
                .amount(form.getAmount())
                .startDate(form.getStartDate())
                .endDate(form.getEndDate())
                .concertImage(concertImagePath)
                .likeCount(0)
                .reviewCount(0)
                .concertType(form.getConcertType())
                .build();
    }
}
