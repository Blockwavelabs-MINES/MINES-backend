package io.propwave.tree.send.application;

import io.propwave.tree.auth.application.util.SocialServiceUtil;
import io.propwave.tree.auth.application.util.UserServiceUtil;
import io.propwave.tree.auth.application.util.WalletServiceUtil;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.User;
import io.propwave.tree.auth.infrastructure.SocialUserRepository;
import io.propwave.tree.auth.infrastructure.UserRepository;
import io.propwave.tree.auth.infrastructure.WalletRepository;
import io.propwave.tree.exception.model.ForbiddenException;
import io.propwave.tree.external.client.ethereum.Web3jService;
import io.propwave.tree.send.application.dto.response.SendInformationResponseService;
import io.propwave.tree.send.application.dto.response.SendTransactionResponseService;
import io.propwave.tree.send.application.util.SendServiceUtil;
import io.propwave.tree.send.domain.SendTransaction;
import io.propwave.tree.send.infrastructure.SendTransactionRepository;
import io.propwave.tree.send.ui.dto.request.SendTransactionRequest;
import io.propwave.tree.send.ui.dto.request.UpdateSendInformationRequest;
import io.propwave.tree.send.ui.dto.response.TransactionListResponse;
import io.propwave.tree.utils.SamTreeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendService {

    private final Web3jService web3jService;

    private final SendTransactionRepository sendTransactionRepository;
    private final UserRepository userRepository;
    private final SocialUserRepository socialUserRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public Map<LocalDate, List<TransactionListResponse>> getTransactionList(Long userId, Long lastLoadedId) {

        SocialUser socialUser = SocialServiceUtil.findSocialUserByUserId(socialUserRepository, userId);

        List<SendTransaction> sendTransactionList = sendTransactionRepository.findAllByLastId(socialUser.getUsername(), lastLoadedId);

        List<TransactionListResponse> transactionListResponseList = sendTransactionList.stream()
                .map(transaction -> {
                    String tickerImageUrl = SamTreeUtil.getTickerImageUrl(transaction.getTokenTicker());
                    String status = web3jService.getTransactionStatus(transaction.getTransactionInformation().getTransactionHash());
                    return TransactionListResponse.of(
                            transaction.getId(),
                            transaction.getCreatedAt().toLocalDate(),
                            tickerImageUrl,
                            transaction.getSenderSocialName(),
                            transaction.getTokenAmount(),
                            status,
                            transaction.getReceiveLinkInformation().getLinkKey()
                    );
                }).toList();

        return transactionListResponseList.stream()
                .collect(Collectors.groupingBy(TransactionListResponse::getCreatedAt));
    }

    @Transactional
    public SendTransactionResponseService saveSendTransaction(Long id, SendTransactionRequest request) {

        User user = UserServiceUtil.findUserById(userRepository, id);

        SocialUser senderSocialUser = SocialServiceUtil.findSocialUserByUserAndUsername(socialUserRepository, user, request.getSenderSocialName());
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
        SendTransaction sendTransaction = SendServiceUtil.findSendTransactionByLinkKey(sendTransactionRepository, linkKey);

        return SendInformationResponseService.of(
                sendTransaction.getId(),
                sendTransaction.getSenderSocialName(),
                sendTransaction.getReceiverSocialName(),
                sendTransaction.getReceiveLinkInformation().getIsLinkValid(),
                sendTransaction.getTokenTicker(),
                sendTransaction.getTokenAmount(),
                sendTransaction.isExpiredAt(sendTransaction.getExpiredAt()),
                sendTransaction.getExpiredAt().withNano(0),
                sendTransaction.getTransactionInformation().getNetworkId(),
                sendTransaction.getReceiveLinkInformation().getLinkKey()
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
        SocialUser receiver = SocialServiceUtil.findSocialUserByUserId(socialUserRepository, id);
        WalletServiceUtil.validNotExistsByWalletAddressAndWalletType(walletRepository, request.getReceiverWalletAddress(), request.getReceiverWalletType());
        SendTransaction sendTransaction = SendServiceUtil.findSendTransactionById(sendTransactionRepository, transactionId);

        if (!sendTransaction.isTransactionOwner(receiver.getUsername(), receiver.getSocialType())) {
            throw new ForbiddenException("접근 권한이 없는 거래입니다.");
        }

        sendTransaction.updateReceiverInformation(request.getReceiverWalletAddress(), request.getReceiverWalletType());
    }

    @Transactional
    public void refund() {

        List<SendTransaction> sendTransactions = sendTransactionRepository.findAllExpiredTransaction();
        for (SendTransaction sendTransaction : sendTransactions) {
            try {
                web3jService.sendGoerliEthToReceiver(sendTransaction.getSenderWalletAddress(), Double.parseDouble(sendTransaction.getTokenAmount()));
                sendTransaction.completeRefund();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
