package com.csrp.csrp.entity;

import com.csrp.csrp.form.DiscountForm;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Discount extends BaseTime{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "concert_info_id")
  private ConcertInfo concertInfo;

  private Integer discountAmount; // 할인 가격

  private LocalDate discountStart;

  private LocalDate discountEnd;

  private Boolean discountActive;

  public static Discount from(DiscountForm form, ConcertInfo concertInfo){
    return Discount.builder()
        .discountActive(false)
        .concertInfo(concertInfo)
        .discountAmount(form.getDiscountAmount())
        .discountStart(form.getDiscountStart())
        .discountEnd(form.getDiscountEnd())
        .build();
  }
}
