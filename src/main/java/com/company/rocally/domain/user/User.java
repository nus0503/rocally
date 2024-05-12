package com.company.rocally.domain.user;

import com.company.rocally.domain.BaseTimeEntity;
import com.company.rocally.domain.partner.Partner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "discovery_route", nullable = false)
    private String discoveryRoute; //선택 입력 필드로

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Partner partner;

    public void addPartner(Partner partner) {
        this.partner = partner;
        partner.addUser(this);
    }

    @Builder
    public User(String username,
                String birthDate,
                String email,
                String password,
                String phoneNumber,
                String discoveryRoute,
                Role role,
                Partner partner) {
        this.username = username;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.discoveryRoute = discoveryRoute;
        this.role = role;
        this.partner = partner;
    }

    public User update(String username) {
        this.username = username;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
