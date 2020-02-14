package org.wesoft.wechat.wxopen.client;

import lombok.Data;

@Data
public class WechatClient {

    private String appID;
    private String appSecret;
    private String token;
    private String encodeAESKey;

    private WechatHelper wechatHelper;

    public WechatClient() {
        super();
    }

    public WechatClient(String appID, String appSecret, String token, String encodeAESKey) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.token = token;
        this.encodeAESKey = encodeAESKey;
        this.wechatHelper = new WechatHelper(appID, appSecret, token, encodeAESKey);
    }

}
