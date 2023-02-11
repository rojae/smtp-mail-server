package io.github.rojae.smtp.controller;

import io.github.rojae.smtp.common.enums.ApiCode;
import io.github.rojae.smtp.dto.ApiBase;
import io.github.rojae.smtp.dto.MailVerifyRequestDto;
import io.github.rojae.smtp.service.MailVerifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VerifyController {

    private final MailVerifyService mailVerifyService;

    @PostMapping("/api/v1/mail/verify/signupForAuth")
    public ResponseEntity<ApiBase<Object>> verify(@RequestBody @Valid MailVerifyRequestDto requestDto){
        if(mailVerifyService.verifyBySecret(requestDto.getSecret(), requestDto.getEmail()))
            return ResponseEntity.ok(new ApiBase<>(ApiCode.STMP_OK));
        else
            return ResponseEntity.ok(new ApiBase<>(ApiCode.INVALID_SECRET));
    }

}
