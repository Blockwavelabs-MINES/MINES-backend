package io.propwave.tree.external.client.dto.twitter;

import io.propwave.tree.external.client.twitter.TweetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TweetRequest {

    @NotNull
    private TweetType tweetType;

    private String comment;

    @NotBlank
    private String tokenTicker;

    @NotBlank
    private String tokenAmount;

    @NotBlank
    private String time;

    private String senderUsername;

    private String receiverUsername;
}
