package io.github.rojae.smtp.common.utils;

import java.time.LocalDateTime;

public class KstUtils {

    public static LocalDateTime now() {
        return LocalDateTime.now().plusHours(9);
    }

    // 데이터는 UTC 저장하고, 사용자에게 메일과 같이 조회하는 화면에서는 KST 변환하자.
    public static LocalDateTime toKST(LocalDateTime dateTime){
        return dateTime.plusHours(9);
    }

}