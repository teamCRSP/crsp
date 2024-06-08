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
@Table(name = "concertDateInfo")
public class ConcertDateInfo {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @OneToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "concert_info_id")
 private ConcertInfo concertInfo;

 private LocalDateTime concertDate;

 private String concertName;

  public static ConcertDateInfo from(ConcertForm form, ConcertInfo concertInfo){
    return ConcertDateInfo.builder()
        .concertInfo(concertInfo)
        .concertDate(form.getDate())
        .concertName(form.getTitle())
        .build();
  }

}
