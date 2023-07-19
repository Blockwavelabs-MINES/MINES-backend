package io.propwave.tree.send.application;

import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.exception.model.ForbiddenException;
import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.send.application.dto.response.SendInformationResponseService;
import io.propwave.tree.send.application.dto.response.SendTransactionResponseService;
import io.propwave.tree.send.domain.SendTransaction;
import io.propwave.tree.send.infrastructure.SendTransactionRepository;
import io.propwave.tree.send.ui.dto.request.SendTransactionRequest;
import io.propwave.tree.send.ui.dto.request.UpdateSendInformationRequest;
import io.propwave.tree.utils.SamTreeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

        SocialUser senderSocialUser = socialUserRepository.findByUserAndUsername(user, request.getSenderSocialName())
                .orElseThrow(() -> new NotFoundException("존재하지 않는 소셜 유저입니다."));

        String linkKey = confirmTheOnlyLinkKey();

        SendTransaction sendTransaction = sendTransactionRepository.save(SendTransaction.newInstance(
                senderSocialUser,
                request.getSenderSocialName(),
                request.getSenderSocialType(),
                request.getSenderWalletAddress(),
                request.getSenderWalletType(),
                request.getReceiverSocialName(),
                request.getReceiverSocialType(),
                request.getTokenTicker(),
                request.getTokenAmount(),
                linkKey,
                request.getTransactionHash(),
                request.getNetworkId(),
                LocalDateTime.now().plusDays(3)
        ));

        return SendTransactionResponseService.of(
                sendTransaction.getReceiveLinkInformation().getLinkKey(),
                sendTransaction.getExpiredAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Transactional
    public SendInformationResponseService getSendInformation(String linkKey) {
        SendTransaction sendTransaction = sendTransactionRepository.findByReceiveLinkInformationLinkKey(linkKey)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 거래의 링크키입니다."));

        return SendInformationResponseService.of(
                sendTransaction.getId(),
                sendTransaction.getSenderSocialName(),
                sendTransaction.getReceiverSocialName(),
                sendTransaction.getReceiveLinkInformation().getIsLinkValid(),
                sendTransaction.getTokenTicker(),
                sendTransaction.getTokenAmount(),
                sendTransaction.isExpiredAt(sendTransaction.getExpiredAt()),
                sendTransaction.getExpiredAt().withNano(0)
        );
    }

    private String confirmTheOnlyLinkKey() {
        String linkKey = "";

        do {
            linkKey = SamTreeUtil.makeRandomString(10);
        } while (sendTransactionRepository.existsByReceiveLinkInformationLinkKey(linkKey));

        return linkKey;
    }

    @Transactional
    public void updateSendInformation(Long id, Long transactionId, UpdateSendInformationRequest request) {
        SocialUser receiver = socialUserRepository.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

        if (!walletRepository.existsByWalletAddressAndWalletType(request.getReceiverWalletAddress(), request.getReceiverWalletType())) {
            throw new NotFoundException("존재하지 않는 지갑입니다.");
        }

        SendTransaction sendTransaction = sendTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 거래입니다."));

        if (!sendTransaction.isTransactionOwner(receiver.getUsername(), receiver.getSocialType())) {
            throw new ForbiddenException("접근 권한이 없는 거래입니다.");
        }

        sendTransaction.updateReceiverInformation(request.getReceiverWalletAddress(), request.getReceiverWalletType());
    }
}
