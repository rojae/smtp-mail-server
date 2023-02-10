package io.github.rojae.smtp.scheduler;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.service.MailQueueService;
import io.github.rojae.smtp.service.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignupScheduler {

    private final MailSendService mailSendService;
    private final MailQueueService mailQueueService;

    @Scheduled(cron = "*/10 * * * * *") // 매 분마다 실행
    public void scheduleSignupTask() {
        List<Mail> top30 = mailQueueService.getTop30();

        for(Mail m : top30){
            try {
                mailSendService.signupAuthMail(m);
                Thread.sleep(10);
            }
            catch (InterruptedException | MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        log.debug("schedule signup mail task's sent - {}", top30.size());
    }

}
