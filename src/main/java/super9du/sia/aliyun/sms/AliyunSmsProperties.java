package super9du.sia.aliyun.sms;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 阿里云短信服务
 */
@Data
@ConfigurationProperties(prefix = AliyunSmsProperties.PREFIX)
public class AliyunSmsProperties implements InitializingBean {

    public static final String PREFIX = "aliyun.sms";
    public static final String ENDPOINT = "dysmsapi.aliyuncs.com";

    private String accessKeyId;

    private String accessKeySecret;

    /**
     * 短信签名
     */
    private String signature;

    /**
     * 短信模板
     * <p>
     * key 为「短信模板编号」，value 为「短信模板变量」
     */
    private Map<String, String> templates;

    public String getTemplateParam(String templateCode) {
        return templates.get(templateCode);
    }

    @Override
    public void afterPropertiesSet() {
        Utils.requireNotBlack(accessKeyId, "properties aliyun.sms.accessKeyId is black");
        Utils.requireNotBlack(accessKeySecret, "properties aliyun.sms.accessKeySecret is black");
        Utils.requireNotBlack(signature, "properties aliyun.sms.signature is black");
        if (templates.size() <= 1) {
            throw new IllegalStateException("properties aliyun.sms.signature templates is not set");
        }
    }
}
