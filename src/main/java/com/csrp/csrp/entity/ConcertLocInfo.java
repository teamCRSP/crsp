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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name = "concertLocInfo")
public class ConcertLocInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;

  @OneToOne(mappedBy = "concertLocInfo")
  private ConcertSeatInfo concertSeatInfoList;

  @OneToMany(mappedBy = "concertLocInfo")
  private List<ConcertDateInfo> concertDateInfo;

  private String concertTitle;

  private List<String> location;

  public static ConcertLocInfo from(ConcertForm form, ConcertInfo concertInfo){
    return ConcertLocInfo.builder()
        .location(form.getLocation())
        .concertInfo(concertInfo)
        .concertTitle(form.getTitle())
        .build();
  }

  public static ConcertLocInfo of(ConcertUpdateForm form) {
    return ConcertLocInfo.builder()
            .location(form.getLocation())
            .concertTitle(form.getTitle())
            .build();
  }
}
