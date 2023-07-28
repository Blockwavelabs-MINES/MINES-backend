package io.propwave.tree.send.infrastructure;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.propwave.tree.send.domain.SendTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static io.propwave.tree.send.domain.QSendTransaction.sendTransaction;

@RequiredArgsConstructor
public class SendTransactionRepositoryCustomImpl implements SendTransactionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SendTransaction> findAllExpiredTransaction() {

        return queryFactory
                .select(sendTransaction)
                .from(sendTransaction)
                .where(
                        sendTransaction.receiveLinkInformation.isLinkExpired.eq(true),
                        sendTransaction.receiveLinkInformation.isLinkValid.eq(false),
                        sendTransaction.isRefund.eq(false),
                        sendTransaction.receiveLinkInformation.linkKey.isNotEmpty()
                )
                .fetch();
    }
}
