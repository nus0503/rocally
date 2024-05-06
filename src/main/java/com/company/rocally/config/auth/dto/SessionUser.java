package com.company.rocally.config.auth.dto;

import com.company.rocally.domain.user.Role;
import com.company.rocally.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionUser implements Serializable {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private Role role;
    private String modifiedDate;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phoneNumber = user.getPhoneNumber();
        this.role = user.getRole();
        this.modifiedDate = user.getModifiedDate();
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
