package io.propwave.tree.auth.application.dto.response;

import io.propwave.tree.auth.domain.WalletType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletResponseService {

    private Long walletId;
    private String walletAddress;
    private WalletType walletType;

    public static WalletResponseService of(Long walletId, String walletAddress, WalletType walletType) {
        return new WalletResponseService(walletId, walletAddress, walletType);
    }
}
