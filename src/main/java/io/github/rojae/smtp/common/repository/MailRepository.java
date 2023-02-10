package io.github.rojae.smtp.common.repository;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.common.enums.MailType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail, Long> {

        Optional<Mail> findBySecretKeyAndEmail(String secretKey, String email);
        List<Mail> findTop30ByMailTypeAndExpireDateAfterOrderByExpireDateAsc(MailType mailType, LocalDateTime now);

}
