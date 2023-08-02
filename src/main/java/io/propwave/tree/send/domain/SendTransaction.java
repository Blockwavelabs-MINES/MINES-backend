package io.propwave.tree.send.domain;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import io.propwave.tree.auth.domain.WalletType;
import io.propwave.tree.common.domain.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendTransaction extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_social_id")
    private SocialUser socialUser;

    @Column(nullable = false)
    private String senderSocialName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType senderSocialType;

    @Column(nullable = false)
    private String senderWalletAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WalletType senderWalletType;

    @Column(nullable = false)
    private String receiverSocialName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType receiverSocialType;

    @Column
    private String receiverWalletAddress;

    @Column
    @Enumerated(EnumType.STRING)
    private WalletType receiverWalletType;

    @Column(nullable = false)
    private String tokenTicker;

    @Column(nullable = false)
    private String tokenAmount;

    @Embedded
    private ReceiveLinkInformation receiveLinkInformation;

    @Embedded
    private TransactionInformation transactionInformation;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isRefund;

    private SendTransaction(
            SocialUser socialUser,
            String senderSocialName,
            SocialType senderSocialType,
            String senderWalletAddress,
            WalletType senderWalletType,
            String receiverSocialName,
            SocialType receiverSocialType,
            String tokenTicker,
            String tokenAmount,
            String linkKey,
            String transactionHash,
            String networkId,
            LocalDateTime expiredAt
    ) {
        this.socialUser = socialUser;
        this.senderSocialName = senderSocialName;
        this.senderSocialType = senderSocialType;
        this.senderWalletAddress = senderWalletAddress;
        this.senderWalletType = senderWalletType;
        this.receiverSocialName = receiverSocialName;
        this.receiverSocialType = receiverSocialType;
        this.tokenTicker = tokenTicker;
        this.tokenAmount = tokenAmount;
        this.receiveLinkInformation = ReceiveLinkInformation.of(linkKey);
        this.transactionInformation = TransactionInformation.of(transactionHash, networkId);
        this.expiredAt = expiredAt;
    }

    public static SendTransaction newInstance(
            SocialUser socialUser,
            String senderSocialName,
            SocialType senderSocialType,
            String senderWalletAddress,
            WalletType senderWalletType,
            String receiverSocialName,
            SocialType receiverSocialType,
            String tokenTicker,
            String tokenAmount,
            String linkKey,
            String transactionHash,
            String networkId,
            LocalDateTime expiredAt
    ) {
        return new SendTransaction(socialUser, senderSocialName, senderSocialType, senderWalletAddress, senderWalletType, receiverSocialName, receiverSocialType, tokenTicker, tokenAmount, linkKey, transactionHash, networkId, expiredAt);
    }

    public void updateReceiverInformation(String receiverWalletAddress, WalletType receiverWalletType) {
        this.receiverWalletAddress = receiverWalletAddress;
        this.receiverWalletType = receiverWalletType;
        this.receiveLinkInformation = ReceiveLinkInformation.completeTransaction();
    }

    public boolean isTransactionOwner(String receiverSocialName, SocialType receiverSocialType) {
        return this.receiverSocialName.equals(receiverSocialName) && this.receiverSocialType.equals(receiverSocialType);
    }

    public boolean isExpiredAt(LocalDateTime expiredAt) {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public void completeRefund() {
        this.isRefund = true;
    }
}
