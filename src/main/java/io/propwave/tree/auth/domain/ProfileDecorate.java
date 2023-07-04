package io.propwave.tree.auth.domain;

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
public class ProfileDecorate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'COLOR'")
    @Column(nullable = false)
    private BackgroundType backgroundType;

    @ColumnDefault("'#ffffff'")
    @Column(nullable = false)
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
}
