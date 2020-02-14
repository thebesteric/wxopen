package org.wesoft.wechat.wxopen.client;

import lombok.Data;
import org.wesoft.wechat.wxopen.api.Guide;

/**
 * @author Eric Joe
 * @version Ver 1.0
 * @info 微信导购助手
 * @build 2020-02-11 15:59
 */
@Data
public abstract class WechatGuideClient {

    public Guide guide;

    public WechatGuideClient() {
        guide = new Guide() {
            @Override
            public String getAloneOrNotAccessToken() {
                return getAloneAccessToken();
            }
        };
    }

    abstract String getAloneAccessToken();

}
