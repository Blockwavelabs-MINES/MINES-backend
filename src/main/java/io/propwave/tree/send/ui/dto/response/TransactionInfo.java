package io.propwave.tree.send.ui.dto.response;

import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionInfo {

    private Long id;

    private LocalDate createdAt;

    private String tickerImageUrl;

    private String senderName;

    private String receiverName;

    private String tokenAmount;

    private String status;

    private String linkKey;

    public static TransactionInfo of(Long id, LocalDate createdAt, String tickerImageUrl, String senderName, String receiverName, String tokenAmount, String status, String linkKey) {
        return new TransactionInfo(id, createdAt, tickerImageUrl, senderName, receiverName, tokenAmount, status, linkKey);
    }
}
