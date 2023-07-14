package io.propwave.tree.send.application;

import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.exception.model.ForbiddenException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.send.application.dto.response.SendTransactionResponseService;
import io.propwave.tree.send.domain.SendTransaction;
import io.propwave.tree.send.infrastructure.SendTransactionRepository;
import io.propwave.tree.send.ui.dto.request.SendTransactionRequest;
import io.propwave.tree.utils.SamTreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SendService {

    private final SendTransactionRepository sendTransactionRepository;
    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public SendTransactionResponseService saveSendTransaction(Long id, SendTransactionRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        Wallet wallet = walletRepository.findByWalletAddress(request.getSenderWalletAddress())
                .orElseThrow(() -> new NotFoundException("등록되지 않은 지갑입니다."));

        if (!wallet.isWalletOwner(user)) {
            throw new ForbiddenException("해당 지갑에 권한이 없습니다.");
        }

        SocialUser socialUser = socialUserRepository.findByUserAndUsername(user, request.getSenderSocialName())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 소셜 유저입니다."));

        String linkKey = confirmTheOnlyLinkKey();

        SendTransaction sendTransaction = sendTransactionRepository.save(SendTransaction.newInstance(
                socialUser,
                request.getSenderWalletAddress(),
                null,
                request.getTokenTicker(),
                request.getTokenAmount(),
                linkKey,
                request.getTransactionHash(),
                request.getTokenContractAddress(),
                request.getNetworkId(),
                LocalDateTime.now().plusDays(3)
        ));

        return SendTransactionResponseService.of(
                sendTransaction.getReceiveLinkInformation().getLinkKey(),
                sendTransaction.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    private String confirmTheOnlyLinkKey() {
        String linkKey = "";

        do {
            linkKey = SamTreeUtil.makeRandomString(10);
        } while (sendTransactionRepository.existsByReceiveLinkInformationLinkKey(linkKey));

        return linkKey;
    }
}
