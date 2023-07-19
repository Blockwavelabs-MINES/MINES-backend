package io.propwave.tree.external.client.dto.twitter;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TweetLink {

    private String link;

    public static TweetLink of(String link) {
        return new TweetLink(link);
    }
}
