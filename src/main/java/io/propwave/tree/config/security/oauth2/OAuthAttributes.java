package io.propwave.tree.config.security.oauth2;

import io.propwave.tree.auth.domain.SocialType;
import io.propwave.tree.config.security.oauth2.social.GoogleOAuth2UserInformation;
import io.propwave.tree.config.security.oauth2.social.TwitterOAuth2UserInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class OAuthAttributes {

    private String attributeKey;
    private OAuth2UserInformation oAuth2UserInformation;

    public static OAuthAttributes of(SocialType socialType, String attributeKey, Map<String, Object> attributes) {
        if (Objects.requireNonNull(socialType) == SocialType.TWITTER) {
            return ofTwitter(attributeKey, attributes);
        }
        return ofGoogle(attributeKey, attributes);
    }

    private static OAuthAttributes ofGoogle(String attributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributeKey(attributeKey)
                .oAuth2UserInformation(new GoogleOAuth2UserInformation(attributes))
                .build();
    }

    private static OAuthAttributes ofTwitter(String attributeKey, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .attributeKey(attributeKey)
                .oAuth2UserInformation(new TwitterOAuth2UserInformation(attributes))
                .build();
    }
}
