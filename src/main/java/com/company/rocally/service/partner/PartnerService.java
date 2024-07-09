package com.company.rocally.service.partner;

import com.company.rocally.common.security.SecurityService;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.partner.dto.PartnerRegistRequestDto;
import com.company.rocally.domain.partner.Partner;
import com.company.rocally.domain.partner.PartnerRepository;
import com.company.rocally.domain.user.User;
import com.company.rocally.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartnerService {

    private final UserRepository userRepository;
    private final PartnerRepository partnerRepository;
    private final SecurityService securityService;


    @Transactional
    public void regist(SessionUser user, PartnerRegistRequestDto partnerRegistRequestDto) {
        User user1 = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new UsernameNotFoundException("해당 유저가 없습니다."));
        Partner partner = Partner.builder()
                .occupation(partnerRegistRequestDto.getOccupation())
                .account(partnerRegistRequestDto.getAccount())
                .bank(partnerRegistRequestDto.getBank())
                .personalSnsLink(partnerRegistRequestDto.getPersonalSnsLink())
                .koreaLanguageLevel(partnerRegistRequestDto.getKoreaLanguageLevel())
                .discoveryRoute(partnerRegistRequestDto.getDiscoveryRoute())
                .build();
        user1.addPartner(partner);
        user1.changeRoleToPartner();
        userRepository.save(user1);
        securityService.updateAuthority();
    }
}
