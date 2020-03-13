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

模块的使用都是基于 WechatHelper 类，可以用过 wechatClient.getWechatHelper() 来得到该类的实例

### 客服模块

```text
/**
 * 添加客服
 *
 * @param account  客服账号
 * @param nickname 客服昵称
 * @param password 客服密码
 */
public JSONObject add(String account, String nickname, String password)

/**
 * 修改客服
 *
 * @param account  客服账号
 * @param nickname 客服昵称
 * @param password 客服密码
 */
public JSONObject update(String account, String nickname, String password)

/**
 * 删除客服
 *
 * @param account  客服账号
 * @param nickname 客服昵称
 * @param password 客服密码
 */
public JSONObject delete(String account, String nickname, String password)

/**
 * 获取所有客服账号
 */
public JSONObject list()

/**
 * 发送文本消息
 *
 * @param toUser  接收者
 * @param content 文本内容
 */
public JSONObject sendText(String toUser, String content)

/**
 * 发送图片消息
 *
 * @param toUser  接收者
 * @param mediaId mediaId
 */
public JSONObject sendImage(String toUser, String mediaId)

/**
 * 发送语音消息
 *
 * @param toUser  接收者
 * @param mediaId mediaId
 */
public JSONObject sendVoice(String toUser, String mediaId)

/**
 * 发送视频消息
 *
 * @param toUser       接收者
 * @param title        标题
 * @param description  描述
 * @param mediaId      mediaId
 * @param thumbMediaId 缩略图
 */
public JSONObject sendVideo(String toUser, String title, String description, String mediaId, String thumbMediaId)

/**
 * 发送音乐消息
 *
 * @param toUser       接收者
 * @param title        标题
 * @param description  描述
 * @param musicUrl     音乐链接
 * @param hqMusicUrl   高品质音乐链接，wifi 环境优先使用该链接播放音乐
 * @param thumbMediaId 缩略图
 */
public JSONObject sendMusic(String toUser, String title, String description, String musicUrl, String hqMusicUrl, String thumbMediaId)

/**
 * 发送图文消息（点击跳转到外链）
 *
 * @param toUser      接收者
 * @param title       标题
 * @param description 描述
 * @param picUrl      图片地址
 * @param redirectUrl 跳转地址
 */
public JSONObject sendNews(String toUser, String title, String description, String picUrl, String redirectUrl)

/**
 * 发送图文消息
 *
 * @param toUser  接收者
 * @param mediaId mediaId
 */
public JSONObject sendMpNews(String toUser, String mediaId)

/**
 * 发送菜单消息
 * <p>
 * 当用户点击后，微信会发送一条XML消息到开发者服务器
 * <xml>
 * <ToUserName><![CDATA[ToUser]]></ToUserName>
 * <FromUserName><![CDATA[FromUser]]></FromUserName>
 * <CreateTime>1500000000</CreateTime>
 * <MsgType><![CDATA[text]]></MsgType>
 * <Content><![CDATA[满意]]></Content>
 * <MsgId>1234567890123456</MsgId>
 * <bizmsgmenuid>101</bizmsgmenuid>
 * </xml>
 *
 * @param toUser      接收者
 * @param headContent 提示头信息
 * @param tailContent 提示尾信息
 * @param list        菜单选项
 */
public JSONObject sendMpMenu(String toUser, String headContent, String tailContent, List<MenuItem> list)

/**
 * 发送卡券
 * <p>
 * 特别注意客服消息接口投放卡券仅支持非自定义Code码和导入code模式的卡券的卡券
 *
 * @param toUser 接收者
 * @param cardId 微信卡卷编号
 */
public JSONObject sendWxCard(String toUser, String cardId)

/**
 * 发送小程序卡片（要求小程序与公众号已关联）
 *
 * @param toUser       接收者
 * @param title        标题
 * @param appId        小程序的 appid，要求小程序的 appid 需要与公众号有关联关系
 * @param pagePath     小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
 * @param thumbMediaId 缩略图
 */
public JSONObject sendMiniProgramPage(String toUser, String title, String appId, String pagePath, String thumbMediaId)
```
