package org.wesoft.wechat.wxopen.client.applet;

import lombok.Getter;
import lombok.Setter;
import org.wesoft.wechat.wxopen.client.WechatHelper;

/**
 * 小程序客户端
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-11 16:08
 */
@Getter
@Setter
public class AppletClient {

    private String appID;
    private String appSecret;
    private AppletHelper appletHelper;

    private AppletClient() {
        super();
    }

    public AppletClient(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.appletHelper = new AppletHelper(this.appID, this.appSecret);
    }

}
