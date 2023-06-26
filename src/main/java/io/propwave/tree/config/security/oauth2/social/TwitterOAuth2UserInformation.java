package io.propwave.tree.config.security.oauth2.social;

import io.propwave.tree.config.security.oauth2.OAuth2UserInformation;

import java.util.Map;

public class TwitterOAuth2UserInformation extends OAuth2UserInformation {

    private final Map<String, Object> data = (Map<String, Object>) attributes.get("data");

    public TwitterOAuth2UserInformation(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return getData(data, "id");
    }

    @Override
    public String getNickname() {
        return getData(data, "username");
    }

    @Override
    public String getImageUrl() {
        return getData(data, "url");
    }
}
