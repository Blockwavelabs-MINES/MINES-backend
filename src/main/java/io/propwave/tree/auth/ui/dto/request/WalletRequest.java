package io.propwave.tree.auth.ui.dto.request;

import io.propwave.tree.auth.domain.WalletType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletRequest {

    @NotBlank
    private String walletAddress;

    @NotNull
    private WalletType walletType;
}
