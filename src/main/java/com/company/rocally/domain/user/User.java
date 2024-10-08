package com.company.rocally.domain.user;

import com.company.rocally.domain.BaseTimeEntity;
import com.company.rocally.domain.partner.Partner;
import com.company.rocally.domain.travel.Reserve;
import com.company.rocally.domain.travel.Travel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "discovery_route")
    private String discoveryRoute; //선택 입력 필드로

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Partner partner;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Travel> travels = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Reserve> reserves = new ArrayList<>();

    public void addPartner(Partner partner) {
        this.partner = partner;
        partner.addUser(this);
    }
    public void changeRoleToPartner() {
        this.role = Role.PARTNER;
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

    public void setNewPassword(String newPassword) {
        this.password = newPassword;
    }
}
