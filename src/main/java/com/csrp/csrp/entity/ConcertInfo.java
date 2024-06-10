package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.type.ConcertType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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

  @OneToOne(mappedBy = "concertInfo"/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
  private ConcertSeatInfo concertSeatInfo;
  //private List<ConcertSeatInfo> seatInfoList = new ArrayList<>();

  @OneToOne(mappedBy = "concertInfo")
  private ConcertDateInfo concertDateInfo;

  @OneToOne(mappedBy = "concertInfo")
  private ConcertLocInfo concertLocInfo;

  private String title;

  private String artist;

  private String description;

  private Integer amount;

  private LocalDateTime date;

  private String location;

  @Column(name = "CONCERTIMAGE")
  private String concertImage;

  @Column(name = "LIKECOUNT")
  private Integer likeCount;

  @Column(name = "REVIEWCOUNT")
  private Integer reviewCount;

  @Enumerated(EnumType.STRING)
  @Column(name = "CONCERTTYPE")
  private ConcertType concertType;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public static ConcertInfo from(ConcertForm form, String concertImagePath){

    return ConcertInfo.builder()
        .title(form.getTitle())
        .artist(form.getArtist())
        .description(form.getDescription())
        .amount(form.getAmount())
        .location(form.getLocation())
        .concertImage(concertImagePath)
        .likeCount(0)
        .reviewCount(0)
        .concertType(form.getConcertType())
        .build();
  }
}
