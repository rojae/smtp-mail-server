package io.github.rojae.smtp.scheduler;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.service.MailQueueService;
import io.github.rojae.smtp.service.MailSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailScheduler {

    private final MailSendService mailSendService;
    private final MailQueueService mailQueueService;

    @Scheduled(cron = "*/10 * * * * *") // 매 분마다 실행
    public void scheduleSignupTask() {
        var top30 = mailQueueService.getTop30Signup();

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

    @Scheduled(cron = "*/10 * * * * *") // 매 분마다 실행
    public void scheduleWelcomeTask() {
        var top30 = mailQueueService.getTop30Welcome();

        for(Mail m : top30){
            try {
                mailSendService.welcomeMail(m);
                Thread.sleep(10);
            }
            catch (InterruptedException | MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        log.debug("schedule welcome mail task's sent - {}", top30.size());
    }

}
