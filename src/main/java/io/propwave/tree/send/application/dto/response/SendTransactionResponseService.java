package io.propwave.tree.send.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendTransactionResponseService {

    private String linkKey;
    private String expiredAt;

    public static SendTransactionResponseService of(String linkKey, String expiredAt) {
        return new SendTransactionResponseService(linkKey, expiredAt);
    }
}
