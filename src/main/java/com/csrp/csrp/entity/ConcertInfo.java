package com.csrp.csrp.entity;


import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.type.ConcertType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CONCERTINFO")
public class ConcertInfo extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "concert_info_id")
  private Long id;

  @OneToMany(mappedBy = "concertInfo")
  private List<ConcertLocInfo> concertLocInfoList;

  @OneToOne(mappedBy = "concertInfo")
  private Discount discount;

  private String title;

  private String artist;

  private String description;

  private LocalDateTime startDate; // 콘서트 시작일

  private LocalDateTime endDate; // 콘서트 종료일

  private Integer seatSPrice;

  private Integer seatAPrice;

  private Integer seatBPrice;

  @Column(name = "CONCERTIMAGE")
  private String concertImage;

  @Column(name = "LIKECOUNT")
  private Integer likeCount;

  @Column(name = "REVIEWCOUNT")
  private Integer reviewCount;

  @Enumerated(EnumType.STRING)
  @Column(name = "CONCERTTYPE")
  private ConcertType concertType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public static ConcertInfo from(ConcertForm form, String concertImagePath, User user) {

    return ConcertInfo.builder()
        .title(form.getTitle())
        .artist(form.getArtist())
        .description(form.getDescription())
        .concertImage(concertImagePath)
        .startDate(form.getStartDate())
        .endDate(form.getEndDate())
        .likeCount(0)
        .reviewCount(0)
        .seatSPrice(form.getSeatSPrice())
        .seatAPrice(form.getSeatAPrice())
        .seatBPrice(form.getSeatBPrice())
        .concertType(form.getConcertType())
        .user(user)
        .build();
  }
}
