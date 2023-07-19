package io.propwave.tree.send.ui.dto.request;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.WalletType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendTransactionRequest {

    @NotBlank
    private String senderSocialName;

    @NotNull
    private SocialType senderSocialType;

    @NotBlank
    private String senderWalletAddress;

    @NotNull
    private WalletType senderWalletType;

    @NotBlank
    private String receiverSocialName;

    @NotNull
    private SocialType receiverSocialType;

    @NotBlank
    private String tokenTicker;

    @NotBlank
    private String tokenAmount;

    @NotBlank
    private String transactionHash;

    @NotBlank
    private String networkId;
}
