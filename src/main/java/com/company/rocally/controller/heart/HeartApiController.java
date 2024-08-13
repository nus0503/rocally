package com.company.rocally.controller.heart;

import com.company.rocally.config.auth.LoginUser;
import com.company.rocally.config.auth.dto.SessionUser;
import com.company.rocally.controller.heart.dto.HeartRequestDto;
import com.company.rocally.service.heart.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class HeartApiController {

    private final HeartService heartService;

    @PostMapping("/api/heart")
    public ResponseEntity<?> insert(@RequestBody HeartRequestDto heartRequestDto, @LoginUser SessionUser user) {
        heartService.insert(heartRequestDto, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/heart")
    public ResponseEntity<?> delete(@RequestBody HeartRequestDto heartRequestDto, @LoginUser SessionUser user) {
        heartService.delete(heartRequestDto, user);
        return ResponseEntity.ok().build();
    }
}
