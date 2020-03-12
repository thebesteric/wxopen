# wxopen 使用
## 与 Spring Boot 整合
- application.yaml
```yaml
wxopen:
  wechat:
    app-id: 公众号的 APPID
    app-secret: 公众号 SECRET
    token: 公众号的 TOKEN
    encode-aes-key: 公众号的 加密key
```

- @Configuration 类配置
```java
@Configuration
public class AssociationConfig {

    @Value("${wxopen.wechat.app-id}")
    private String wechatAppId;
    
    @Value("${wxopen.wechat.app-secret}")
    private String wechatSecret;

    @Value("${wxopen.wechat.token}")
    private String wechatToken;

    @Value("${wxopen.wechat.encode-aes-key}")
    private String wechatEncodeAesKey;

    @Bean
    public WechatClient wechatClient() {
        return new WechatClient(wechatAppId, wechatSecret, wechatToken, wechatEncodeAesKey);
    }
}
```

## API 介绍