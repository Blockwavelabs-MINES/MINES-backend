package io.propwave.tree.auth.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String walletAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @Builder
    private Wallet(User user, String walletAddress, WalletType walletType) {
        this.user = user;
        this.walletAddress = walletAddress;
        this.walletType = walletType;
    }

    public static Wallet newInstance(User user, String walletAddress, WalletType walletType) {
        return new Wallet(user, walletAddress, walletType);
    }

    public boolean isWalletOwner(User user) {
        return this.user.equals(user);
    }
}
