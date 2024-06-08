package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertForm;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "concertLocInfo")
public class ConcertLocInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;

  private String location;

  private String concertTitle;


  public static ConcertLocInfo from(ConcertForm form, ConcertInfo concertInfo){
    return ConcertLocInfo.builder()
        .concertInfo(concertInfo)
        .location(form.getLocation())
        .concertTitle(form.getTitle())
        .build();
  }
}
