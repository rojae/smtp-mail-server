package io.github.rojae.smtp.service;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.common.repository.MailRepository;
import io.github.rojae.smtp.common.utils.KstUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSendService {
    private final JavaMailSender mailSender;
    private final MailRepository mailRepository;

    @Value("${web.location.signin}")
    public String signinUrl;

    @Value("${spring.mail.username}")
    public String fromAddress;


    @Transactional(readOnly = false)
    public void signupAuthMail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        LocalDateTime kstTime = KstUtils.toKST(mail.getExpireDate());
        String htmlMsg
                = "인증코드 : " + mail.getSecretKey() + "<br/>" +
                  "만료일자 : " + kstTime.getYear() + "년 " + kstTime.getMonthValue() + "월 " + kstTime.getDayOfMonth() + "일 " + kstTime.getHour() + "시 " + kstTime.getMinute() + "분 " + kstTime.getSecond() + "초 <br/>";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject("[Company] - 회원가입 인증메일입니다");
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);

        // Update isAuth Column
        Optional<Mail> selectedMail = mailRepository.findById(mail.getId());
        selectedMail.ifPresent(value -> value.setSendDate(LocalDateTime.now()));

        // needs :: api level's isAuth -> 'Y' updated process
    }


    public void welcomeMail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg
                = "가입이 완료되었습니다!";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject("[Company] - 가입완료 메일입니다");
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);
    }
}