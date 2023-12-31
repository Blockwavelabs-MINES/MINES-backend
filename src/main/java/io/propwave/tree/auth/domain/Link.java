package io.propwave.tree.auth.domain;

import io.propwave.tree.common.domain.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String linkTitle;

    @Column(nullable = false, length = 2083)
    private String linkUrl;


    @Builder
    private Link(String linkTitle, String linkUrl, User user) {
        this.linkTitle = linkTitle;
        this.linkUrl = linkUrl;
        this.user = user;
    }

    public static Link newInstance(String linkTitle, String linkUrl, User user) {
        return new Link(linkTitle, linkUrl, user);
    }

    public void update(String linkTitle, String linkUrl) {
        this.linkTitle = linkTitle;
        this.linkUrl = linkUrl;
    }

    public boolean isLinkUser(User user) {
        return this.user.equals(user);
    }
}
