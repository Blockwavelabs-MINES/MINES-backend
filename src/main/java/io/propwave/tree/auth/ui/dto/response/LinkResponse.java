package io.propwave.tree.auth.ui.dto.response;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkResponse {

    private Long id;
    private String linkTitle;
    private String linkUrl;

    public static LinkResponse of(Long id, String linkTitle, String linkUrl) {
        return new LinkResponse(id, linkTitle, linkUrl);
    }
}
