package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.WalletResponseService;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.auth.ui.dto.request.WalletRequest;
import io.propwave.tree.exception.model.ConflictException;
import io.propwave.tree.exception.model.ForbiddenException;
import io.propwave.tree.exception.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public List<WalletResponseService> getWalletList(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        List<Wallet> walletList = walletRepository.findAllByUserId(user.getId());
        return walletList.stream()
                .map(wallet -> WalletResponseService.of(wallet.getId(), wallet.getWalletAddress(), wallet.getWalletType()))
                .collect(Collectors.toList());
    }

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

    @Transactional
    public void deleteWallet(Long id, Long walletId) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 지갑입니다."));

        if (!wallet.getUser().equals(user)) {
            throw new ForbiddenException("해당 지갑에 권한이 없습니다.");
        }

        walletRepository.delete(wallet);
    }
}
