package super9du.sia.aliyun.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import lombok.Getter;

public class AliyunSmsTemplate {
    @Getter
    private final Client client;
    private final AliyunSmsProperties aliyunSmsProperties;

    public AliyunSmsTemplate(Client client, AliyunSmsProperties aliyunSmsProperties) {
        this.client = client;
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    /**
     * 使用指定的「短信模板编号」发送短信
     *
     * @param templateCode 阿里云「短信模板编号」
     * @param phoneNumber  发送的手机号
     * @return {@link SendSmsResponse}
     */
    public SendSmsResponse send(String templateCode, String phoneNumber) throws Exception {
        return client.sendSms(createSendSmsRequest(templateCode, phoneNumber));
    }

    /**
     * 根据指定的「短信模板编号」生成 {@link SendSmsRequest}
     *
     * @param templateCode 阿里云「短信模板编号」
     * @param phoneNumber  发送的手机号
     * @return SendSmsResponse
     */
    public SendSmsRequest createSendSmsRequest(String templateCode, String phoneNumber) {
        return new SendSmsRequest()
                .setSignName(aliyunSmsProperties.getSignature())
                .setTemplateCode(templateCode)
                .setTemplateParam(aliyunSmsProperties.getTemplateParam(templateCode))
                .setPhoneNumbers(phoneNumber);
    }
}
