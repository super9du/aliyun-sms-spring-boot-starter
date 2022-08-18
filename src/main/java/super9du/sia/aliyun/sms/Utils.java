package super9du.sia.aliyun.sms;

import org.springframework.util.StringUtils;

final class Utils {
    public static void requireNotBlack(String s, String message) {
        if (StringUtils.hasText(s)) {
            return;
        }
        throw new IllegalStateException(message);
    }
}
