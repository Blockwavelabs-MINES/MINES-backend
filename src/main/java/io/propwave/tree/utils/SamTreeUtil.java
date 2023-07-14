package io.propwave.tree.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SamTreeUtil {

    private static final Random random = new Random();

    public static String makeUserIdByEmail(String email) {
        String[] emailSplitByAtSign = email.split("@");
        String[] emailPlatform = emailSplitByAtSign[1].split("\\.");

        return emailSplitByAtSign[0] + "_" + emailPlatform[0];
    }

    public static String makeRandomString(int length) {
        StringBuilder link = new StringBuilder();

        while(link.length() < length) {
            int randomInt = random.nextInt(75) + 48;
            if (randomInt >= 58 && randomInt <= 64 || randomInt >= 91 && randomInt <= 96) {
                continue;
            }

            link.append((char) randomInt);
        }

        return link.toString();
    }
}
