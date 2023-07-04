package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Wallet;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface WalletRepository extends Repository<Wallet, Long> {

    // CREATE
    void save(Wallet wallet);

    // READ
    Optional<Wallet> findById(Long id);
    boolean existsByWalletAddress(String walletAddress);

    // UPDATE

    // DELETE
    void delete(Wallet wallet);
}
