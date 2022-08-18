package super9du.sia.aliyun.sms;

import com.aliyun.dysmsapi20170525.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.util.Map;

@SuppressWarnings("deprecation")
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TestApplication.class, args);
        AliyunSmsProperties aliyunSmsProperties = ctx.getBean(AliyunSmsProperties.class);
        Client client = ctx.getBean(Client.class);
        Assert.notNull(client);
        AliyunSmsTemplate template = ctx.getBean(AliyunSmsTemplate.class);
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
        Assert.state("templateCode1".equals(templates.get("templateCode1")));
        Assert.state("templateCode2".equals(templates.get("templateCode2")));
    }

}
