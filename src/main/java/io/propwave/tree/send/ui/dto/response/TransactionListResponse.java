package io.propwave.tree.send.ui.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionListResponse {

    private Map<LocalDate, List<TransactionInfo>> transactions;

    private Long lastLoadedId;

    public static TransactionListResponse of(Map<LocalDate, List<TransactionInfo>> transactions, Long lastLoadedId) {
        return new TransactionListResponse(transactions, lastLoadedId);
    }
}
