# aliyun-sms-spring-boot-starter

阿里云短信服务 spring-boot-starter。

```
        JDK 版本: 11
Spring Boot 版本: 2.7.2
```

在实际开发中短信模板编号可以定义为枚举类，短信模板参数可以通过配置文件修改，无需修改代码。

用法
---

1. 配置（`application.properties`）
    ```properties
    aliyun.sms.accessKeyId=accessKeyId                  # 访问key(必)
    aliyun.sms.accessKeySecret=accessKeySecret          # 访问密钥(必)
    aliyun.sms.signature=signature                      # 短信签名(必)
    aliyun.sms.templates.templateCode1=templateCode1    # 短信模板编号和对应的短信模板变量(至少要配置一个键值对)
    aliyun.sms.templates.templateCode2=templateCode2
    ```

2. 代码使用
   ```java
   import com.aliyun.dysmsapi20170525.Client;
   import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
   import super9du.sia.aliyun.sms.AliyunSmsTemplate;
   
   class Demo {
       /**
        * 获取阿里云 sms 原生的客户端对象
        */
       @Inject
       Client client;
   
       @Inject
       AliyunSmsTemplate template;
   
       void test() throws Exception {
           // 在实际开发中短信模板编号可以定义为枚举类，短信模板参数可以通过配置文件修改。
           SendSmsResponse resp1 = template.send("短信模板编号", "13245678901");
   
           // 使用阿里云原生的客户端能力
           client.sendSms(template.createSendSmsRequest("短信模板编号", "13245678901"));
           // client.xxx()
       }
   }
   ```