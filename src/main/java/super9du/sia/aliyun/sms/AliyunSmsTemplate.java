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

import java.util.Map;

public class AliyunSmsTemplate {
    @Getter
    private final Client client;
    private final AliyunSmsProperties aliyunSmsProperties;

    public AliyunSmsTemplate(Client client, AliyunSmsProperties aliyunSmsProperties) {
        this.client = client;
        this.aliyunSmsProperties = aliyunSmsProperties;
    }

    /**
     * 向指定手机号发送手机验证码。
     * <p>
     * <strong>仅在 {@link AliyunSmsProperties#getTemplates()} 的 size 为 1 时可直接使用，否则将抛出异常</strong>
     *
     * @param phoneNumber 发送的手机号
     * @return 已发送的手机验证码
     * @throws IllegalStateException 使用阿里云客户端发送短信的响应与预期不符
     */
    public String sendAuthCode(String phoneNumber) throws Exception {
        String templateName, authCode;
        templateName = aliyunSmsProperties.getTemplateNameIfOnlyOne();
        authCode = Utils.generateAuthCode();
        SendSmsResponse resp = send(phoneNumber, templateName, Map.of("code", authCode));
        if (resp == null) {
            throw new AliyunSmsStarterException("response of aliyun sms is empty");
        }
        if (resp.getBody() != null && "OK".equals(resp.getBody().getCode())) {
            return authCode;
        }
        throw new AliyunSmsStarterException(resp.getBody().getMessage());
    }

    /**
     * @param phoneNumber   接收短信的手机号
     * @param templateName  短信模板名称（用于获取配置的短信模板编号）
     * @param templateParam 短信模板变量（kv结构）
     * @return {@link SendSmsResponse}
     */
    public SendSmsResponse send(String phoneNumber, String templateName, Map<String, String> templateParam) throws Exception {
        return client.sendSms(createSendSmsRequest(phoneNumber, templateName, templateParam));
    }

    /**
     * @param phoneNumber   接收短信的手机号
     * @param templateCode  短信模板编号
     * @param templateParam 短信模板变量（JSON字符串）
     * @return {@link SendSmsResponse}
     */
    public SendSmsResponse send(String phoneNumber, String templateCode, String templateParam) throws Exception {
        return client.sendSms(createSendSmsRequest(phoneNumber, templateCode, templateParam));
    }

    /**
     * @param phoneNumber   接收短信的手机号
     * @param templateName  短信模板名称（用于获取配置的短信模板编号）
     * @param templateParam 短信模板变量（kv结构）
     * @return {@link SendSmsRequest}
     */
    public SendSmsRequest createSendSmsRequest(String phoneNumber, String templateName, Map<String, String> templateParam) {
        String templateCode = aliyunSmsProperties.getTemplateCode(templateName);
        return createSendSmsRequest(phoneNumber, templateCode, Utils.toJsonObject(templateParam));
    }

    /**
     * @param phoneNumber   接收短信的手机号
     * @param templateCode  短信模板编号
     * @param templateParam 短信模板变量（JSON字符串）
     * @return {@link SendSmsRequest}
     */
    public SendSmsRequest createSendSmsRequest(String phoneNumber, String templateCode, String templateParam) {
        return new SendSmsRequest()
                .setSignName(aliyunSmsProperties.getSignature())
                .setTemplateCode(templateCode)
                .setTemplateParam(templateParam)
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
