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
