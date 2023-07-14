package io.propwave.tree.send.infrastructure;

import io.propwave.tree.send.domain.SendTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendTransactionRepository extends JpaRepository<SendTransaction, Long> {

    // CREATE

    // READ
    boolean existsByReceiveLinkInformationLinkKey(String linkKey);

    // UPDATE

    // DELETE
}
