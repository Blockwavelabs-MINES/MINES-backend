package io.propwave.tree.external.client.dto.twitter;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TweetMedia {

    private String[] mediaIds;

    public static TweetMedia of(String mediaIds) {
        return new TweetMedia(new String[] {mediaIds});
    }
}
