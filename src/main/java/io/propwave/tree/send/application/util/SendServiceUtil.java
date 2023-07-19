package io.propwave.tree.send.application.util;

import io.propwave.tree.exception.model.NotFoundException;
import io.propwave.tree.send.domain.SendTransaction;
import io.propwave.tree.send.infrastructure.SendTransactionRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SendServiceUtil {

    public static SendTransaction findSendTransactionById(SendTransactionRepository sendTransactionRepository, Long id) {
        return sendTransactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 거래입니다."));
    }

    public static SendTransaction findSendTransactionByLinkKey(SendTransactionRepository sendTransactionRepository, String linkKey) {
        return sendTransactionRepository.findByReceiveLinkInformationLinkKey(linkKey)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 거래의 링크키입니다."));
    }
}
