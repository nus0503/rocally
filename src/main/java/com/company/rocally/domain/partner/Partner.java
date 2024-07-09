package com.company.rocally.domain.partner;

import com.company.rocally.domain.BaseTimeEntity;
import com.company.rocally.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "partner")
public class Partner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;

    @Column(name = "occupation", nullable = false)
    private String occupation;


    @Column(name = "korea_language_level", nullable = false)
    private String koreaLanguageLevel;

    @Column(name = "bank", nullable = false)
    private String bank;

    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @Column(name = "personal_sns_link", unique = true)
    private String personalSnsLink;

    @Column(name = "discoveryRoute")
    private String discoveryRoute;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Partner(String occupation, String koreaLanguageLevel, String bank, String account, String personalSnsLink, String discoveryRoute) {
        this.occupation = occupation;
        this.koreaLanguageLevel = koreaLanguageLevel;
        this.bank = bank;
        this.account = account;
        this.personalSnsLink = personalSnsLink;
        this.discoveryRoute = discoveryRoute;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
