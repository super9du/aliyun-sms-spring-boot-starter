package super9du.sia.aliyun.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class UtilsTest {

    @Test
    void testToJsonList() {
        Assert.state(Objects.equals("[\"13245678901\"]", Utils.toJsonList(List.of("13245678901"))));
        Assert.state(Objects.equals("[\"aliyun sms API\",\"em...\"]", Utils.toJsonList(List.of("aliyun sms API", "em..."))));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void toJsonObject() throws JsonProcessingException {
        Map<String, String>[] origins = new Map[]{
                Map.<String, String>of("1", "a"),
                Map.<String, String>of("1", "a", "2", "b"),
                Map.<String, String>of("1", "a", "2", "b", "3", "c")
        };
        String[] expecteds = new String[]{
                "{\"1\":\"a\"}",
                "{\"1\":\"a\",\"2\":\"b\"}",
                "{\"1\":\"a\",\"2\":\"b\",\"3\":\"c\"}"
        };
        for (int i = 0; i < origins.length; i++) {
            Map<String, String> origin = origins[i];
            String expected = expecteds[i];
            String actual = Utils.toJsonObject(origin);
            Map reverseActual = new ObjectMapper().readValue(actual, Map.class);
            Map reverseExpected = new ObjectMapper().readValue(expected, Map.class);
            Assert.state(Objects.equals(reverseActual, reverseExpected),
                    MessageFormat.format("Unexpected {0}", actual));
        }
    }

}