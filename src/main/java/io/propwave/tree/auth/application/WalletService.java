package io.propwave.tree.auth.application;

import io.propwave.tree.auth.application.dto.response.WalletResponseService;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.application.util.WalletServiceUtil;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.auth.ui.dto.request.WalletRequest;
import io.propwave.tree.exception.model.ForbiddenException;
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

        User user = UserServiceUtil.findUserByUserId(userRepository, userId);
        List<Wallet> walletList = walletRepository.findAllByUserId(user.getId());

        return walletList.stream()
                .map(wallet -> WalletResponseService.of(wallet.getId(), wallet.getWalletAddress(), wallet.getWalletType()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerWallet(Long id, WalletRequest walletRequest) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        WalletServiceUtil.validNotExistsByWalletAddress(walletRepository, walletRequest.getWalletAddress());

        walletRepository.save(Wallet.newInstance(
                user,
                walletRequest.getWalletAddress(),
                walletRequest.getWalletType()
        ));
    }

    @Transactional
    public void deleteWallet(Long id, Long walletId) {

        User user = UserServiceUtil.findUserById(userRepository, id);
        Wallet wallet = WalletServiceUtil.findWalletById(walletRepository, walletId);

        if (!wallet.isWalletOwner(user)) {
            throw new ForbiddenException("해당 지갑에 권한이 없습니다.");
        }

        walletRepository.delete(wallet);
    }
}
