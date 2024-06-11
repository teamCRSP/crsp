package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.form.ConcertUpdateForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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


  @OneToOne
  private ConcertLocInfo concertLocInfo;

  private Integer seatS;
  private Integer seatSPrice;

  private Integer seatA;
  private Integer seatAPrice;

  private Integer seatB;
  private Integer seatBPrice;


  public static ConcertSeatInfo from(ConcertForm form, ConcertLocInfo concertLocInfo){
    return ConcertSeatInfo.builder()
        .concertLocInfo(concertLocInfo)
        .seatS(form.getSeatS())
        .seatSPrice(form.getSeatSPrice())
        .seatA(form.getSeatAPrice())
        .seatAPrice(form.getSeatAPrice())
        .seatB(form.getSeatB())
        .seatBPrice(form.getSeatBPrice())
        .build();
  }

}
