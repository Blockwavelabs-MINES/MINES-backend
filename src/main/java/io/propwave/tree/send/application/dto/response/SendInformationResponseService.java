package io.propwave.tree.send.application.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendInformationResponseService {

    private String senderUsername;
    private Boolean isValid;
    private String tokenTicker;
    private String tokenAmount;

    public static SendInformationResponseService of(String senderUsername, Boolean isValid, String tokenTicker, String tokenAmount) {
        return new SendInformationResponseService(senderUsername, isValid, tokenTicker, tokenAmount);
    }
}
