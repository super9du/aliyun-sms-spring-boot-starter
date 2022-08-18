package super9du.sia.aliyun.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@ConditionalOnClass(Client.class)
@EnableConfigurationProperties(AliyunSmsProperties.class)
@Configuration
public class AliyunSmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Client smsClient(AliyunSmsProperties aliyunSmsProperties) throws Exception {
        return new Client(new Config()
                .setAccessKeyId(aliyunSmsProperties.getAccessKeyId())
                .setAccessKeySecret(aliyunSmsProperties.getAccessKeySecret())
                .setEndpoint(AliyunSmsProperties.ENDPOINT));
    }

    @Bean
    @ConditionalOnMissingBean
    public AliyunSmsTemplate aliyunSmsTemplate(Client client, AliyunSmsProperties aliyunSmsProperties) {
        return new AliyunSmsTemplate(client, aliyunSmsProperties);
    }
}
