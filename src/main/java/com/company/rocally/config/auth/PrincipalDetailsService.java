package com.company.rocally.config.auth;

import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * UserDetailsService 인터페이스를 구현하고, loadUserByUsername() 메소드를 오버라이딩해서 시큐리티에서
 * /login을 했을 때 사용자 정보를 가져오는 로직 작성
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원정보가 일치하지 않습니다."));
        return new PrincipalDetails(user);
    }
}
