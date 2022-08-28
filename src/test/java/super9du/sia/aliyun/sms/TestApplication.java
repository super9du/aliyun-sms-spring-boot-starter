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

import com.aliyun.dysmsapi20170525.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Map;

@SuppressWarnings("deprecation")
@SpringBootTest(classes = {AliyunSmsAutoConfiguration.class})
public class TestApplication {

    @Autowired
    AliyunSmsProperties aliyunSmsProperties;

    @Autowired
    Client client;

    @Autowired
    AliyunSmsTemplate template;

    @Test
    void normal() {
        Assert.notNull(client);
        Assert.notNull(template);
        checkAliyunSmsProperties(aliyunSmsProperties);
        System.out.println("FINISH");
    }

    static void checkAliyunSmsProperties(AliyunSmsProperties aliyunSmsProperties) {
        System.out.println(aliyunSmsProperties.toString());
        Assert.notNull(aliyunSmsProperties, "AliyunSmsProperties is null");
        String accessKeyId = aliyunSmsProperties.getAccessKeyId();
        String accessKeySecret = aliyunSmsProperties.getAccessKeySecret();
        String signature = aliyunSmsProperties.getSignature();
        Map<String, String> templates = aliyunSmsProperties.getTemplates();
        Assert.state("accessKeyId".equals(accessKeyId), "aliyun.sms.accessKeyId is not expected");
        Assert.state("accessKeySecret".equals(accessKeySecret), "aliyun.sms.accessKeySecret is not expected");
        Assert.state("signature".equals(signature), "aliyun.sms.signature is not expected");
        Assert.state(templates.size() == 2);
        Assert.state("templateCode1".equals(templates.get("yourTemplateName1")));
        Assert.state("templateCode2".equals(templates.get("yourTemplateName2")));
    }

}
