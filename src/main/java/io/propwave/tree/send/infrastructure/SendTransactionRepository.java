package io.propwave.tree.send.infrastructure;

import io.propwave.tree.send.domain.SendTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SendTransactionRepository extends JpaRepository<SendTransaction, Long>, SendTransactionRepositoryCustom {

    // CREATE

    // READ
    Optional<SendTransaction> findByReceiveLinkInformationLinkKey(String linkKey);
    boolean existsByReceiveLinkInformationLinkKey(String linkKey);

    // UPDATE

    // DELETE
}
