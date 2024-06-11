package com.csrp.csrp.entity;

import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.form.ConcertUpdateForm;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "concert_loc_id")
 private ConcertLocInfo concertLocInfo;

 private LocalDateTime concertStartDate;

 private LocalDateTime concertEndDate;

 private String concertName;

 private HashMap<String, List<LocalDateTime>> concertTime = new HashMap<>();


  public static ConcertDateInfo from(ConcertForm form, List<LocalDateTime> concertDateInfo, ConcertLocInfo concertLoc){

   HashMap<String, List<LocalDateTime>> concertTime = new HashMap<>();
   concertTime.put(form.getTitle(), concertDateInfo);

   return ConcertDateInfo.builder()
        .concertTime(concertTime)
        .concertLocInfo(concertLoc)
        .concertStartDate(form.getStartDate())
        .concertEndDate(form.getEndDate())
        .concertName(form.getTitle())
        .build();
  }


}
