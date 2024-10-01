package com.company.rocally.config;

import com.company.rocally.config.auth.CustomOAuth2UserService;
import com.company.rocally.config.handler.CustomAuthFailureHandler;
import com.company.rocally.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthFailureHandler customAuthFailureHandler;

    private final UserDetailsService userService;

    /**
     * 특정 HTTP 요청 패턴에 대한 보안 구성 무시(인증, 권환 검사 등 적용 X)
     */
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return web -> web.ignoring().mvcMatchers(
//                "/**"
//        );
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("[+] WebSecurityConfig Start !!! ");
        http.antMatcher("/**")
                .authorizeRequests()
//                .antMatchers("/api/v1/**").hasRole(Role.PARTNER.name())
//                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .antMatchers("/**"/*, "/css/**", "/images/**", "/js/**", "/h2-console/**", "/api/**", "/news/**", "/signup", "/loginForm", "/user"*/).permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                    .cors().disable()
                    .csrf().disable()
                    .headers().frameOptions().disable()
//                .and()
//                    .authorizeRequests()
//                    .anyRequest().permitAll()
                .and()
                    .logout().logoutSuccessUrl("/")
                .and()
                    .formLogin()
                        .loginPage("/loginForm")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureHandler(customAuthFailureHandler)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .and()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
        return http.build();

    }

    /**
     * 패스워드 인코더로 사용할 빈 등록
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 인증 관리자 관련 설정
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return daoAuthenticationProvider;
    }
}
