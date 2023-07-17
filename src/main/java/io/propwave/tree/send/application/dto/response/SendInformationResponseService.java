package io.propwave.tree.send.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendInformationResponseService {

    private Long transactionId;
    private String senderUsername;
    private Boolean isValid;
    private String tokenTicker;
    private String tokenAmount;

    public static SendInformationResponseService of(Long transactionId, String senderUsername, Boolean isValid, String tokenTicker, String tokenAmount) {
        return new SendInformationResponseService(transactionId, senderUsername, isValid, tokenTicker, tokenAmount);
    }
}
