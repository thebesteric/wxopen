package org.wesoft.wechat.wxopen.client;

public class WechatThirdClient {

    public String componentAppId;
    public String componentAppSecret;
    public String componentToken;
    public String componentEncodeAesKey;

    private WechatHelper wechatHelper;

    public WechatThirdClient() {
        super();
    }

    public WechatThirdClient(String componentAppId, String componentAppSecret, String componentToken, String componentEncodeAesKey) {
        this.componentAppId = componentAppId;
        this.componentAppSecret = componentAppSecret;
        this.componentToken = componentToken;
        this.componentEncodeAesKey = componentEncodeAesKey;
    }


}
