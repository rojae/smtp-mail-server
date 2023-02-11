package io.github.rojae.smtp.service;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.common.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailVerifyService {

    private final MailRepository mailRepository;

    public boolean verifyBySecret(String secret, String email){
        Optional<Mail> bySecretKeyAndEmail = mailRepository.findBySecretKeyAndEmail(secret, email);
        return bySecretKeyAndEmail.isPresent();
    }

}
