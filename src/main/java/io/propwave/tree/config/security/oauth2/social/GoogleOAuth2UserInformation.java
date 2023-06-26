package io.propwave.tree.config.security.oauth2.social;

import io.propwave.tree.config.security.oauth2.OAuth2UserInformation;

import java.util.Map;

public class GoogleOAuth2UserInformation extends OAuth2UserInformation {

    public GoogleOAuth2UserInformation(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
