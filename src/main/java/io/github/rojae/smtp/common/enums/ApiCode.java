package io.github.rojae.smtp.common.enums;

public enum ApiCode {

    STMP_OK("M0000", "성공"),

    // API Level //
    FAIL_SENDMAIL("M0001", "메일 발송에 실패했습니다"),
    INVALID_SECRET("M0002", "잘못된 인증코드입니다"),

    // UNKNOWN //
    UNKNOWN("UNKNOWN", "알 수 없음");

    private final String code;
    private final String reason;

    ApiCode(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public static ApiCode ofCode(String code){
        for(var e : ApiCode.values()){
            if(e.getCode().equals(code)){
                return e;
            }
        }
        return UNKNOWN;
    }
}
