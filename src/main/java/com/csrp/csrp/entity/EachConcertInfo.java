package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertUpdateForm;
import com.csrp.csrp.form.EachConcertInfoForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EachConcertInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "concert_loc_info_id")
  private ConcertLocInfo concertLocInfo;

  private LocalDateTime concertStartDate;

  private LocalDateTime concertEndDate;

  private String concertLocation;

  private String concertName;

  private Integer seatS;
  private Integer seatA;
  private Integer seatB;

  public static EachConcertInfo from(EachConcertInfoForm form, String concertName,
      String concertLocation, ConcertLocInfo concertLoc){

    return EachConcertInfo.builder()
        .concertLocInfo(concertLoc)
        .concertStartDate(form.getStartDate())
        .concertEndDate(form.getEndDate())
        .concertName(concertName)
        .concertLocation(concertLocation)
        .seatS(form.getSeatS())
        .seatA(form.getSeatA())
        .seatB(form.getSeatB())
        .build();
  }
}
