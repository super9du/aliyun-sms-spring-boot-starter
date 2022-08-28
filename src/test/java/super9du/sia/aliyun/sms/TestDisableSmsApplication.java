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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.util.Arrays;

@SuppressWarnings("deprecation")
@ActiveProfiles("disable")
@SpringBootTest(classes = {AliyunSmsAutoConfiguration.class})
public class TestDisableSmsApplication {

    @Autowired
    ApplicationContext ctx;

    @Test
    void disableAliyunSmsAutoConfiguration() {
        try {
            String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
            System.out.println(Arrays.toString(activeProfiles));
            Assert.state(activeProfiles.length == 1);
            Assert.state("disable".equals(activeProfiles[0]));
            ctx.getBean(AliyunSmsProperties.class);
        } catch (NoSuchBeanDefinitionException e) {
            System.out.println("FINISH");
            return;
        }
        throw new RuntimeException("aliyun-sms has not been disabled");
    }

}
