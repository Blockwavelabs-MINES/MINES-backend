package io.propwave.tree.send.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendInformationResponseService {

    private Long transactionId;
    private String senderUsername;
    private String receiverUsername;
    private Boolean isValid;
    private String tokenTicker;
    private String tokenAmount;
    private Boolean isExpired;
    private LocalDateTime expiredAt;
    private String networkId;
    private String linkKey;

    public static SendInformationResponseService of(Long transactionId, String senderUsername, String receiverUsername, Boolean isValid, String tokenTicker, String tokenAmount, Boolean isExpired, LocalDateTime expiredAt, String networkId, String linkKey) {
        return new SendInformationResponseService(transactionId, senderUsername, receiverUsername, isValid, tokenTicker, tokenAmount, isExpired, expiredAt, networkId, linkKey);
    }
}
