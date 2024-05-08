package com.company.rocally.config.auth;

import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * 스프링 시큐리티가 로그인 요청을 가로채 로그인을 진행하고 완료 되면 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유한 세션저장소에 저장 해준다.
 */
@Getter
@Setter
public class PrincipalDetails implements UserDetails {

    private SessionUser user;

    public PrincipalDetails(User user) {
        this.user = new SessionUser(user);
    }

    /**
     * 유저 권환 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(() -> user.getRoleKey());
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
