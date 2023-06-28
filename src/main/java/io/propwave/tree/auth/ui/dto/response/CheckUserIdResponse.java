package io.propwave.tree.auth.ui.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckUserIdResponse {

    private Boolean isDuplicate;

    public static CheckUserIdResponse of(Boolean isDuplicate) {
        return new CheckUserIdResponse(isDuplicate);
    }
}
