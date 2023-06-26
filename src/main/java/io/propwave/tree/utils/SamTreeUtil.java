package io.propwave.tree.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamTreeUtil {

    public static String makeUserIdByEmail(String email) {
        String[] emailSplitByAtSign = email.split("@");
        String[] emailPlatform = emailSplitByAtSign[1].split("\\.");

        return emailSplitByAtSign[0] + "_" + emailPlatform[0];
    }
}
