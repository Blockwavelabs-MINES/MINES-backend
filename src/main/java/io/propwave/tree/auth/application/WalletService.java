package io.propwave.tree.auth.application;

import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.auth.ui.dto.request.WalletRequest;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public void registerWallet(Long id, WalletRequest walletRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if(walletRepository.existsByWalletAddress(walletRequest.getWalletAddress())) {
           throw new ConflictException("이미 등록된 지갑입니다.");
        }

        walletRepository.save(Wallet.newInstance(
                user,
                walletRequest.getWalletAddress(),
                walletRequest.getWalletType()
        ));
    }
}
