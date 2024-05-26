package umc.spring.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.spring.domain.common.BaseEntity;
import umc.spring.domain.enums.Gender;
import umc.spring.domain.enums.MemberSatus;
import umc.spring.domain.enums.SocialType;
import umc.spring.domain.mapping.MemberCategory;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.domain.mapping.MemberTerms;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 20)
    private String name;

    @Column(nullable = false,length = 40)
    private String email;

    @Column(nullable=false, length=15)
    private String phone;

    @Column(nullable=false, length=10)
    private String userCity;

    @Column(nullable=false, length=40)
    private String address;

    @Column(nullable=false, length=20)
    private String addressDetail;

    private Integer myPoint;

    private LocalDate inactivateDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(15) DEFAULT 'ACTIVATE'")
    private MemberSatus status;

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MemberTerms> memberTermsList=new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<MemberMission> memberMissionList=new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Review> reviewList=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberCategory> memberCategoryList=new ArrayList<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Alarm> alarmList=new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Inquiry> inquiryList=new ArrayList<>();


}
