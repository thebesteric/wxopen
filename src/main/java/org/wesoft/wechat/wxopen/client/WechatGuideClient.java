package org.wesoft.wechat.wxopen.client;

import lombok.Data;
import org.wesoft.wechat.wxopen.api.Guide;

/**
 * 微信导购助手（独立启动类）
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-02-11 15:59
 */
@Data
public abstract class WechatGuideClient {

    public Guide guide;

    public WechatGuideClient(String appID, String appSecret) {
        guide = new Guide(appID, appSecret) {
            @Override
            public String getAloneOrNotAccessToken() {
                return getAloneAccessToken();
            }
        };
    }

    abstract String getAloneAccessToken();

}
