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

    @Override
    public List<SendTransaction> findAllByLastId(String socialName, Long lastLoadedId) {

        return queryFactory
                .select(sendTransaction)
                .from(sendTransaction)
                .where(
                        lastLoadedId == null ? null : sendTransaction.id.lt(lastLoadedId),
                        sendTransaction.senderSocialName.eq(socialName).or(sendTransaction.receiverSocialName.eq(socialName))
                )
                .orderBy(sendTransaction.id.desc())
                .limit(10)
                .fetch();
    }
}
