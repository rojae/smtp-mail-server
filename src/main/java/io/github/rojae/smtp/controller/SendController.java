package io.github.rojae.smtp.controller;

import io.github.rojae.smtp.common.enums.ApiCode;
import io.github.rojae.smtp.dto.ApiBase;
import io.github.rojae.smtp.dto.MailRequestDto;
import io.github.rojae.smtp.service.MailQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SendController {

    private final MailQueueService mailQueueService;

    @PostMapping("/api/v1/mail/send/signupForAuth")
    public ResponseEntity<ApiBase<Object>> sendAuthMail(@RequestBody MailRequestDto requestDto){
        mailQueueService.insertNew(requestDto);
        return ResponseEntity.ok(new ApiBase<>(ApiCode.STMP_OK));
    }

    @PostMapping("/api/v1/mail/send/signupDone")
    public ResponseEntity<ApiBase<Object>> sendWelcomeMail(){
        return ResponseEntity.ok(new ApiBase<>(ApiCode.STMP_OK));
    }


}
