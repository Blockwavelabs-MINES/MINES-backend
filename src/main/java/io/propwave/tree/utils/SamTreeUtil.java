package io.propwave.tree.utils;

import io.propwave.tree.auth.domain.Language;
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

    public static String makeSendTweetContent(Language language, String comment, String tokenTicker, String tokenAmount, String time, String receiverUsername) {
        StringBuilder sb = new StringBuilder();

        if (language.equals(Language.KOR)) {
            sb
                    .append("3TREE를 통해 @").append(receiverUsername).append(" 에게 ").append(tokenAmount).append(" ").append(tokenTicker).append(" 를 보냈어요 🥳\n")
                    .append("⏰ 보낸 시간 | ").append(time)
                    .append("👇 @").append(receiverUsername).append(" 에게 보내는 메세지\n")
                    .append("\n")
                    .append(comment).append("\n")
                    .append("https://3tree.io").append("\n");
        } else {
            sb
                    .append("I sent ").append(tokenAmount).append(" ").append(tokenTicker).append(" to @").append(receiverUsername).append(" through 3TREE 🥳\n")
                    .append("⏰ Remittance Time | ").append(time).append("\n")
                    .append("👇 Message to @").append(receiverUsername).append("\n")
                    .append("\n")
                    .append(comment).append("\n")
                    .append("https://3tree.io").append("\n");
        }

        return sb.toString();
    }

    public static String makeReceiveTweetContent(Language language, String tokenTicker, String tokenAmount, String time, String senderUsername) {
        StringBuilder sb = new StringBuilder();

        if (language.equals(Language.KOR)) {
            sb
                    .append("3TREE를 통해 @").append(senderUsername).append("에게서 ").append(tokenAmount).append(" ").append(tokenTicker).append("를 받았어요 🥳\n")
                    .append("\n")
                    .append("⏰ 받은 시간 | ").append(time).append("\n")
                    .append("💰 토큰 수량 | ").append(tokenAmount).append(" ").append(tokenTicker).append("\n")
                    .append("시세를 알고 싶다면? https://coinmarketcap.com/ko/").append("\n")
                    .append("https://3tree.io/sendme").append("\n");
        } else {
            sb
                    .append("I received ").append(tokenAmount).append(" ").append(tokenTicker).append(" from @").append(senderUsername).append(" via 3TREE 🥳\n")
                    .append("\n")
                    .append("⏰ Receive Time | ").append(time).append("\n")
                    .append("💰 Token Amount | ").append(tokenAmount).append(" ").append(tokenTicker).append("\n")
                    .append("If you want to know the market price? https://coinmarketcap.com/ko/").append("\n")
                    .append("https://3tree.io/sendme").append("\n");
        }

        return sb.toString();
    }
}
