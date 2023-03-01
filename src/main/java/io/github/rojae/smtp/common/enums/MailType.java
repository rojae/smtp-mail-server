package io.github.rojae.smtp.common.enums;

import lombok.Getter;

// 인증메일 타입 (SIGNUP : 회원가입, WELCOME: 가입완료, RESETPWD : 비밀번호 변경)
@Getter
public enum MailType {
    SIGNUP,
    WELCOME,
    RESETPWD,
}