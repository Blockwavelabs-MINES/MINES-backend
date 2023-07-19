package io.propwave.tree.auth.application.util;

import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.domain.WalletType;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletServiceUtil {

    public static Wallet findWalletById(WalletRepository walletRepository, Long walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 지갑입니다."));
    }

    public static Wallet findWalletByWalletAddress(WalletRepository walletRepository, String walletAddress) {
        return walletRepository.findByWalletAddress(walletAddress)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 지갑입니다."));
    }

    public static void validNotExistsByWalletAddress(WalletRepository walletRepository, String walletAddress) {
        if (walletRepository.existsByWalletAddress(walletAddress)) {
            throw new ConflictException("이미 등록된 지갑입니다.");
        }
    }

    public static void validNotExistsByWalletAddressAndWalletType(WalletRepository walletRepository, String walletAddress, WalletType walletType) {
        if (walletRepository.existsByWalletAddressAndWalletType(walletAddress, walletType)) {
            throw new NotFoundException("존재하지 않는 지갑입니다.");
        }
    }
}
