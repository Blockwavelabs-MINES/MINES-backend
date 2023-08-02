package io.propwave.tree.auth.domain;

import io.propwave.tree.common.domain.AuditingTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileDecorate extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'COLOR'")
    @Column(nullable = false)
    private BackgroundType backgroundType;

    @ColumnDefault("'#ffffff'")
    private String backgroundColor;

    private String backgroundImg;

    @ColumnDefault("'#ffffff'")
    @Column(nullable = false)
    private String buttonColor;

    @ColumnDefault("'#000000'")
    @Column(nullable = false)
    private String buttonFontColor;

    @ColumnDefault("'#000000'")
    @Column(nullable = false)
    private String fontColor;

    private ProfileDecorate(User user) {
        this.user = user;
    }

    public static ProfileDecorate newInstance(User user) {
        return new ProfileDecorate(user);
    }

    public void update(BackgroundType backgroundType, String backgroundColor, String backgroundImg, String buttonColor, String buttonFontColor, String fontColor) {
        this.backgroundType = backgroundType;
        this.backgroundColor = backgroundColor;
        this.backgroundImg = backgroundImg;
        this.buttonColor = buttonColor;
        this.buttonFontColor = buttonFontColor;
        this.fontColor = fontColor;
    }
}
