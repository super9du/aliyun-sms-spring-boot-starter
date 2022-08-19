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
import com.aliyun.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendBatchSmsResponse;
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
     * @return {@link SendSmsRequest}
     */
    public SendSmsRequest createSendSmsRequest(String templateCode, String phoneNumber) {
        return new SendSmsRequest()
                .setSignName(aliyunSmsProperties.getSignature())
                .setTemplateCode(templateCode)
                .setTemplateParam(aliyunSmsProperties.getTemplateParam(templateCode))
                .setPhoneNumbers(phoneNumber);
    }

    /**
     * 批量发送短信
     * <p>
     * <strong>注意</strong>：signatureList、phoneNumberList、templateParamList 三个参数的长度相等，
     * 且应该是一一对应的。即 signatureList[0]、phoneNumberList[0]、templateParamList[0]
     * 三个一组确定一个短信由谁发给谁，发送什么内容。
     *
     * @param templateCode      阿里云「短信模板编号」
     * @param signatureList     阿里云「短信签名」列表
     * @param phoneNumberList   手机号的列表
     * @param templateParamList 阿里云「短信模板变量」的列表
     * @return {@link SendBatchSmsRequest}
     */
    public SendBatchSmsResponse sendBatch(
            String templateCode,
            Iterable<CharSequence> signatureList,
            Iterable<CharSequence> phoneNumberList,
            Iterable<CharSequence> templateParamList
    ) throws Exception {
        return client.sendBatchSms(createSendBatchSmsRequest(
                templateCode, signatureList, phoneNumberList, templateParamList));
    }

    /**
     * 根据指定参数生成 {@link SendBatchSmsRequest}
     * <p>
     * <strong>注意</strong>：signatureList、phoneNumberList、templateParamList 三个参数的长度相等，
     * 且应该是一一对应的。即 signatureList[0]、phoneNumberList[0]、templateParamList[0]
     * 三个一组确定一个短信由谁发给谁，发送什么内容。
     *
     * @param templateCode      阿里云「短信模板编号」
     * @param signatureList     阿里云「短信签名」列表
     * @param phoneNumberList   手机号的列表
     * @param templateParamList 阿里云「短信模板变量」的列表
     * @return {@link SendBatchSmsRequest}
     */
    public SendBatchSmsRequest createSendBatchSmsRequest(
            String templateCode,
            Iterable<CharSequence> signatureList,
            Iterable<CharSequence> phoneNumberList,
            Iterable<CharSequence> templateParamList
    ) {
        return new SendBatchSmsRequest()
                .setSignNameJson(Utils.toJsonList(signatureList))
                .setPhoneNumberJson(Utils.toJsonList(phoneNumberList))
                .setTemplateCode(templateCode)
                .setTemplateParamJson(Utils.toJsonList(templateParamList));
    }
}
