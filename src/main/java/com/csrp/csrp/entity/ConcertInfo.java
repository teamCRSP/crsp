package com.csrp.csrp.entity;


import com.csrp.csrp.form.ConcertForm;
import com.csrp.csrp.form.ConcertUpdateForm;
import com.csrp.csrp.type.ConcertType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CONCERTINFO")
public class ConcertInfo extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concert_info_id")
    private Long id;

    @OneToMany(mappedBy = "concertInfo")
    private List<ConcertLocInfo> concertLocInfoList = new ArrayList<>();

//    @OneToOne(mappedBy = "concertInfo")
//    private Discount discount;

    private String title;

    private String artist;

    private String description;

    private Integer amount;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(name = "CONCERTIMAGE")
    private String concertImage;

    @Column(name = "LIKECOUNT")
    private Integer likeCount;

    @Column(name = "REVIEWCOUNT")
    private Integer reviewCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONCERTTYPE")
    private ConcertType concertType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

    public static ConcertInfo from(ConcertForm form, String concertImagePath) {
        return ConcertInfo.builder()
                .title(form.getTitle())
                .artist(form.getArtist())
                .description(form.getDescription())
                .amount(form.getAmount())
                .concertImage(concertImagePath)
                .startDate(form.getStartDate())
                .endDate(form.getEndDate())
                .likeCount(0)
                .reviewCount(0)
                .concertType(form.getConcertType())
                .build();
    }

}
