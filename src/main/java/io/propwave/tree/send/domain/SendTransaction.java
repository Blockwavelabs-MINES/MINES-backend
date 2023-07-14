package io.propwave.tree.send.domain;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.auth.domain.SocialUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendTransaction {

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
    private String receiverSocialName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType receiverSocialType;

    @Column
    private String receiverWalletAddress;

    @Column(nullable = false)
    private String tokenTicker;

    @Column(nullable = false)
    private Double tokenAmount;

    @Embedded
    private ReceiveLinkInformation receiveLinkInformation;

    @Embedded
    private TransactionInformation transactionInformation;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    private SendTransaction(
            SocialUser socialUser,
            String senderSocialName,
            SocialType senderSocialType,
            String senderWalletAddress,
            String receiverSocialName,
            SocialType receiverSocialType,
            String tokenTicker,
            Double tokenAmount,
            String linkKey,
            String transactionHash,
            String tokenContractAddress,
            String networkId,
            LocalDateTime expiredAt
    ) {
        this.socialUser = socialUser;
        this.senderSocialName = senderSocialName;
        this.senderSocialType = senderSocialType;
        this.senderWalletAddress = senderWalletAddress;
        this.receiverSocialName = receiverSocialName;
        this.receiverSocialType = receiverSocialType;
        this.tokenTicker = tokenTicker;
        this.tokenAmount = tokenAmount;
        this.receiveLinkInformation = ReceiveLinkInformation.of(linkKey);
        this.transactionInformation = TransactionInformation.of(transactionHash, tokenContractAddress, networkId);
        this.expiredAt = expiredAt;
    }

    public static SendTransaction newInstance(
            SocialUser socialUser,
            String senderSocialName,
            SocialType senderSocialType,
            String senderWalletAddress,
            String receiverSocialName,
            SocialType receiverSocialType,
            String tokenTicker,
            Double tokenAmount,
            String linkKey,
            String transactionHash,
            String tokenContractAddress,
            String networkId,
            LocalDateTime expiredAt
    ) {
        return new SendTransaction(socialUser, senderSocialName, senderSocialType, senderWalletAddress, receiverSocialName, receiverSocialType, tokenTicker, tokenAmount, linkKey, transactionHash, tokenContractAddress, networkId, expiredAt);
    }
}
