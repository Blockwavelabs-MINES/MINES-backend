package io.propwave.tree.send.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReceiveLinkInformation {

    @Column(nullable = false)
    private String linkKey;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isLinkExpired = false;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isLinkValid = true;

    private ReceiveLinkInformation(String linkKey) {
        this.linkKey = linkKey;
    }

    protected static ReceiveLinkInformation of(String linkKey) {
        return new ReceiveLinkInformation(linkKey);
    }

    protected static ReceiveLinkInformation completeTransaction() {
        return new ReceiveLinkInformation("", true, false);
    }
}
