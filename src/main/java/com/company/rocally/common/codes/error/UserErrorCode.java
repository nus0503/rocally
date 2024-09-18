package com.company.rocally.common.codes.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    INACTIVE_USER(HttpStatus.FORBIDDEN, "User is inactive"),
    NOT_FOUND_INFORMATION(HttpStatus.NOT_FOUND, "등록된 정보가 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
