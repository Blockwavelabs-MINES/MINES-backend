package io.propwave.tree.send.infrastructure;

import io.propwave.tree.send.domain.SendTransaction;

import java.util.List;

public interface SendTransactionRepositoryCustom {

    List<SendTransaction> findAllExpiredTransaction();
    List<SendTransaction> findAllByLastId(String socialName, Long lastLoadedId);
}
