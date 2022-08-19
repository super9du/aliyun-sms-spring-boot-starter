package super9du.sia.aliyun.sms;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;

public class TestUtils {

    @Test
    void testToJsonList() {
        Assert.state(Objects.equals("[\"13245678901\"]", Utils.toJsonList(List.of("13245678901"))));
        Assert.state(Objects.equals("[\"aliyun sms API\",\"em...\"]", Utils.toJsonList(List.of("aliyun sms API", "em..."))));
    }

}
