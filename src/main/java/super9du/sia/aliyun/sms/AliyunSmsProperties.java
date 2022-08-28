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

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 阿里云短信服务
 */
@Data
@ConfigurationProperties(prefix = AliyunSmsProperties.PREFIX)
public class AliyunSmsProperties implements InitializingBean {

    public static final String PREFIX = "aliyun.sms";
    public static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    private Boolean enabled = true;

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signature;

    /**
     * key 为「短信模板编号名称」，value 为「短信模板编号」
     */
    private Map<String, String> templates;

    public String getTemplateCode(String templateName) {
        return templates.get(templateName);
    }

    /**
     * 如果只配置了一个 TemplateCode，则返回该 TemplateCode；
     * 否则抛出异常。
     *
     * @return 短信模板编号
     */
    public String getTemplateCodeIfOnlyOne() {
        int size = templates.size();
        if (size != 1) throw new AliyunSmsStarterException("仅能在只配置了一个 TemplateCode 时使用");
        return templates.values().stream().findFirst().get();
    }

    @Override
    public void afterPropertiesSet() {
        Assert.hasText(accessKeyId, "properties aliyun.sms.accessKeyId is black");
        Assert.hasText(accessKeySecret, "properties aliyun.sms.accessKeySecret is black");
        Assert.hasText(signature, "properties aliyun.sms.signature is black");
        if (templates.size() < 1) {
            throw new IllegalStateException("properties aliyun.sms.templates require at least one template-code");
        }
    }

}
