package com.company.rocally.common.security;

import com.company.rocally.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{



    @Override
    public void updateAuthority() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oauthAuthentication = (OAuth2AuthenticationToken) authentication;

            Set<GrantedAuthority> updatedAuthorities = new LinkedHashSet<>();
            updatedAuthorities.add(new SimpleGrantedAuthority(Role.PARTNER.getKey()));

//            ArrayList<GrantedAuthority> updatedAuthorities = new ArrayList<>(oauthAuthentication.getAuthorities());
//            updatedAuthorities.add(new SimpleGrantedAuthority(Role.PARTNER.getKey()));

            OAuth2AuthenticationToken newOAuth2AuthenticationToken = new OAuth2AuthenticationToken(oauthAuthentication.getPrincipal(),
                    updatedAuthorities, oauthAuthentication.getAuthorizedClientRegistrationId());
            newOAuth2AuthenticationToken.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(newOAuth2AuthenticationToken);
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken formAuthentication = (UsernamePasswordAuthenticationToken) authentication;

            Set<GrantedAuthority> updatedAuthorities = new LinkedHashSet<>();
            updatedAuthorities.add(new SimpleGrantedAuthority(Role.PARTNER.getKey()));

//            ArrayList<GrantedAuthority> updatedAuthorities = new ArrayList<>(formAuthentication.getAuthorities());
//            updatedAuthorities.add(new SimpleGrantedAuthority(Role.PARTNER.getKey()));

            UsernamePasswordAuthenticationToken newFormAuthenticationToken = new UsernamePasswordAuthenticationToken(formAuthentication.getPrincipal(),
                    formAuthentication.getCredentials(), updatedAuthorities);
            newFormAuthenticationToken.setDetails(authentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(newFormAuthenticationToken);
        }
    }


}
