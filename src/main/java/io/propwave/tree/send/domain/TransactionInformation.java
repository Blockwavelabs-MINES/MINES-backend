package io.propwave.tree.send.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionInformation {

    @Column(nullable = false)
    private String transactionHash;

    @Column(nullable = false)
    private String networkId;

    protected static TransactionInformation of(String transactionHash, String networkId) {
        return new TransactionInformation(transactionHash, networkId);
    }
}
