package io.propwave.tree.send.ui.dto.request;

import io.propwave.tree.auth.domain.SocialType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendTransactionRequest {

    private String senderSocialName;
    private SocialType senderSocialType;
    private String senderWalletAddress;
    private String receiverSocialName;
    private SocialType receiverSocialType;
    private String tokenTicker;
    private Double tokenAmount;
    private String transactionHash;
    private String tokenContractAddress;
    private String networkId;
}
