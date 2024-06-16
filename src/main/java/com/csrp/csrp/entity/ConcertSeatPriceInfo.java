package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertForm;
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
public class ConcertSeatPriceInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @OneToOne
  private ConcertLocInfo concertLocInfo;

  private String concertLoc;

  private Integer seatSPrice;

  private Integer seatAPrice;

  private Integer seatBPrice;


  public static ConcertSeatPriceInfo from(ConcertForm form,String location, ConcertLocInfo concertLocInfo){
    return ConcertSeatPriceInfo.builder()
        .concertLocInfo(concertLocInfo)
        .concertLoc(location)
        .seatSPrice(form.getSeatSPrice())
        .seatAPrice(form.getSeatAPrice())
        .seatBPrice(form.getSeatBPrice())
        .build();
  }

}
