package io.github.rojae.smtp.common.repository;

import io.github.rojae.smtp.common.domain.Mail;
import io.github.rojae.smtp.common.enums.MailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MailRepository extends JpaRepository<Mail, Long> {

        Optional<Mail> findBySecretKeyAndEmail(String secretKey, String email);

        // 전송하지 않았고, 인증하지 않았으며, 만료일지 현재시간보다 미래인 경우
        List<Mail> findTop30ByMailTypeAndSendDateAndIsAuthAndIsEnableAndExpireDateAfterOrderByExpireDateAsc(MailType mailType, LocalDateTime now1, char isAuth, char isEnable, LocalDateTime now2);

        List<Mail> findTop30ByMailTypeAndSendDateOrderByExpireDateAsc(MailType mailType, LocalDateTime now1);

        @Modifying
        @Query("UPDATE TBL_MAIL_REQ m SET m.isEnable = 'N' WHERE m.email = :email AND m.mailType = :mailType")
        void updateToDisable(String email, String mailType);

}
