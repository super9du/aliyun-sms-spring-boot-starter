/*
 * aliyun-sms-spring-boot-starter
 * Copyright 2022  super9du
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package super9du.sia.aliyun.sms;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

final class Utils {

    public static String toJsonList(Iterable<CharSequence> strList) {
        return "[\"" + String.join("\",\"", strList) + "\"]";
    }

    public static String toJsonObject(Map<String, String> map) {
        StringBuilder buf = new StringBuilder();
        buf.append('{');
        map.forEach((k, v) -> buf.append('"').append(k).append('"').append(':')
                .append('"').append(v).append('"').append(','));
        buf.deleteCharAt(buf.length() - 1);
        buf.append('}');
        return buf.toString();
    }

    public static String generateAuthCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(9000) + 1000);
    }

}
