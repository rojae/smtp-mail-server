package io.github.rojae.smtp.service;

import io.github.rojae.smtp.common.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailSendService {
    private final JavaMailSender mailSender;

    @Value("${web.location.signin}")
    public String signinUrl;

    @Value("${spring.mail.username}")
    public String fromAddress;


    public void signupAuthMail(Mail mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg
                = "인증코드 : " + mail.getSecretKey() + "<br/>" +
                  "만료일자 : " + mail.getExpireDate().getYear() + "년 " + mail.getExpireDate().getMonthValue() + "월 " + mail.getExpireDate().getDayOfMonth() + "일 " + mail.getExpireDate().getHour() + "시 " + mail.getExpireDate().getMinute() + "분 " + mail.getExpireDate().getSecond() + "초 <br/>";

        helper.setText(htmlMsg, true); // Use this or above line.
        helper.setTo(mail.getEmail());
        helper.setSubject("[Company] - 회원가입 인증메일입니다");
        helper.setFrom(fromAddress);
        mailSender.send(mimeMessage);
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