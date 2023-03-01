package io.github.rojae.smtp.service;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.common.enums.MailType;
import io.github.rojae.smtp.common.repository.MailRepository;
import io.github.rojae.smtp.dto.MailRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailQueueService {

    private final MailRepository mailRepository;
    private final static int keyLen = 6;
    private final static int expireMin = 30;

    @Transactional(readOnly = false)
    public void insertNew(MailRequestDto requestDto){
        Mail newMail = new Mail();

        if(requestDto.getMailType().equals(MailType.SIGNUP.name())){
            newMail.setSecretKey(RandomStringUtils.randomAlphabetic(keyLen));
            newMail.setExpireDate(LocalDateTime.now().plusMinutes(expireMin));
            newMail.setEmail(requestDto.getEmail());
            newMail.setMailType(MailType.valueOf(requestDto.getMailType()));
        }
        else if(requestDto.getMailType().equals(MailType.WELCOME.name())) {
            newMail.setEmail(requestDto.getEmail());
            newMail.setMailType(MailType.valueOf(requestDto.getMailType()));
        }

        mailRepository.save(newMail);
    }

    public List<Mail> getTop30Signup(){
        // 회원가입 메일이며, 전송하지 않았고, 인증하지 않았으며, 만료일지 현재시간보다 미래인 경우
        return mailRepository.findTop30ByMailTypeAndSendDateAndIsAuthAndExpireDateAfterOrderByExpireDateAsc(MailType.SIGNUP, null, 'N', LocalDateTime.now());
    }

    public List<Mail> getTop30Welcome() {
        // 가입완료 메일이며, 전송하지 않은 경우
        return mailRepository.findTop30ByMailTypeAndSendDateOrderByExpireDateAsc(MailType.WELCOME, null);
    }
}
