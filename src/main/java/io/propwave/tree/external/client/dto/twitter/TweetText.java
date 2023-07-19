package io.propwave.tree.external.client.dto.twitter;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TweetText {

    private String text;
    // private TweetMedia media;

    public static TweetText of(String text) {
        return new TweetText(text);
    }
}
