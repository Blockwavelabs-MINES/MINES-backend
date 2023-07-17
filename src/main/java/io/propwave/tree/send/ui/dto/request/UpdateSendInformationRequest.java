package io.propwave.tree.send.ui.dto.request;

import io.propwave.tree.auth.domain.WalletType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSendInformationRequest {

    private String receiverWalletAddress;
    private WalletType receiverWalletType;
}
