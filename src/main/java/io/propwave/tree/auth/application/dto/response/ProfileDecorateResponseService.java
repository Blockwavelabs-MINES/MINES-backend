package io.propwave.tree.auth.application.dto.response;

import io.propwave.tree.auth.domain.BackgroundType;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileDecorateResponseService {

    private BackgroundType backgroundType;
    private String backgroundColor;
    private String backgroundImg;
    private String buttonColor;
    private String buttonFontColor;
    private String fontColor;
    private String userId;

    public static ProfileDecorateResponseService of(
            BackgroundType backgroundType,
            String backgroundColor,
            String backgroundImg,
            String buttonColor,
            String buttonFontColor,
            String fontColor,
            String userId
    ) {
        return new ProfileDecorateResponseService(
                backgroundType,
                backgroundColor,
                backgroundImg,
                buttonColor,
                buttonFontColor,
                fontColor,
                userId
        );
    }
}
