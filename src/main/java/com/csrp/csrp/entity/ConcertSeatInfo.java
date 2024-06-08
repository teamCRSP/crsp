package com.csrp.csrp.entity;

import com.csrp.csrp.form.AddConcertSeatForm;
import com.csrp.csrp.form.ConcertForm;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "concertSeatInfo")
public class ConcertSeatInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;

  private Integer seatS;
  private Integer seatSPrice;

  private Integer seatA;
  private Integer seatAPrice;

  private Integer seatB;
  private Integer seatBPrice;


  public static ConcertSeatInfo from(ConcertForm form, ConcertInfo concertInfo){
    return ConcertSeatInfo.builder()
        .concertInfo(concertInfo)
        .seatS(form.getSeatS())
        .seatSPrice(form.getSeatSPrice())
        .seatA(form.getSeatAPrice())
        .seatAPrice(form.getSeatAPrice())
        .seatB(form.getSeatB())
        .seatBPrice(form.getSeatBPrice())
        .build();
  }

  public static ConcertSeatInfo of(AddConcertSeatForm form){
    return ConcertSeatInfo.builder()
        .seatS(form.getSeatS())
        .seatSPrice(form.getSeatSPrice())
        .seatA(form.getSeatAPrice())
        .seatAPrice(form.getSeatAPrice())
        .seatB(form.getSeatB())
        .seatBPrice(form.getSeatBPrice())
        .build();
  }
}
