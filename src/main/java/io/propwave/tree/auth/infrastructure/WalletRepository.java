package io.propwave.tree.auth.infrastructure;

import io.propwave.tree.auth.domain.Wallet;
import io.propwave.tree.auth.domain.WalletType;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends Repository<Wallet, Long> {

    // CREATE
    void save(Wallet wallet);

    // READ
    Optional<Wallet> findById(Long id);
    Optional<Wallet> findByWalletAddress(String walletAddress);
    boolean existsByWalletAddressAndWalletType(String walletAddress, WalletType walletType);
    List<Wallet> findAllByUserId(Long id);
    boolean existsByWalletAddress(String walletAddress);

    // UPDATE

    // DELETE
    void delete(Wallet wallet);
}
