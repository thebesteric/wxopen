package org.wesoft.wechat.wxopen.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * WechatProp
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-02-25 20:18
 */
@Component
@ConfigurationProperties(prefix = "wxopen.wechat")
@Getter
@Setter
public class WechatProp {
    private String appId;
    private String appSecret;
    private String token;
    private String encodeAesKey = null;
}
