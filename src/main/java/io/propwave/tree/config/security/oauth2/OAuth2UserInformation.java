package io.propwave.tree.config.security.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2UserInformation {

    protected Map<String, Object> attributes;

    protected String getData(Map<String, Object> data, String attributeKey) {
        if (data == null) {
            return null;
        }

        return (String) data.get(attributeKey);
    }

    public abstract String getId();

    public abstract String getNickname();

    public abstract String getImageUrl();
}
