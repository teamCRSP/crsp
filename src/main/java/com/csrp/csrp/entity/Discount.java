package com.csrp.csrp.entity;

import com.csrp.csrp.form.DiscountForm;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

  private LocalDateTime discountStart;

  private LocalDateTime discountEnd;

  @ColumnDefault("false")
  private Boolean discountActive;

  public static Discount from(DiscountForm form, ConcertInfo concertInfo){
    return Discount.builder()
        .concertInfo(concertInfo)
        .discountAmount(form.getDiscountAmount())
        .discountStart(form.getDiscountStart())
        .discountEnd(form.getDiscountEnd())
        .build();
  }
}
