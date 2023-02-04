package com.zerobase.cms.order.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    ALERADY_REGIESTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
