package io.github.rojae.smtp.dto;

import io.github.rojae.smtp.common.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailRequestDto {
    private String email;
    private String mailType;
}
