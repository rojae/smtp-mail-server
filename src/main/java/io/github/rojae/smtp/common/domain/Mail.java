package io.github.rojae.smtp.common.domain;

import io.github.rojae.smtp.common.enums.MailType;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "TBL_MAIL_REQ")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Mail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "isAuth", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'", length = 1)
    private char isAuth = 'N';

    @Column(name = "expireDate", nullable = false)
    private LocalDateTime expireDate;

    @Column(name = "secretKey", nullable = false)
    private String secretKey;

    @Column(name = "sendDate", nullable = false)
    @CreatedDate
    private LocalDateTime sendDate;

    @Column(name = "mailType", nullable = false, columnDefinition = "VARCHAR(20)", length = 20)
    @Enumerated(EnumType.STRING)
    private MailType mailType;

}