package io.propwave.tree.auth.domain;

import io.propwave.tree.common.model.EnumModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role implements EnumModel {

    USER("USER", "사용자"),
    ADMIN("ADMIN", "관리자");

    private final String key;
    private final String title;

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return key;
    }
}
