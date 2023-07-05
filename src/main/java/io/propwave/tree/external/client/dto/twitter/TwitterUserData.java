package io.propwave.tree.external.client.dto.twitter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitterUserData {

    private String id;
    private String username;
}
