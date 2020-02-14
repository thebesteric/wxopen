package org.wesoft.wechat.wxopen.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.wechat.wxopen.client.crypt.AesException;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;

public class WechatThirdHelper extends WechatThirdHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(WechatThirdHelper.class);

    public WechatThirdHelper(String componentAppId, String componentAppSecret, String componentToken, String componentEncodeAesKey) {
        WechatThirdHelperSupport.componentAppId = componentAppId;
        WechatThirdHelperSupport.componentAppSecret = componentAppSecret;
        WechatThirdHelperSupport.componentToken = componentToken;
        if (StringUtils.isNotEmpty(componentEncodeAesKey)) {
            try {
                this.crypt = new WXBizMsgCrypt(componentToken, componentEncodeAesKey, componentAppId);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 生成 URL 授权链接
     *
     * @param redirectUrl 回调地址
     * @param authType    授权模式 1.公众号展示, 2.小程序展示, 3.公众号和小程序都展示
     *
     * @return 授权链接
     */
    public String createComponentLoginPage(String redirectUrl, int... authType) {
        String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage" +
                "?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=%s";
        int _authType = 3;
        if (authType != null && authType.length > 0) {
            _authType = authType[0];
        }
        return String.format(url, componentAppId, getPreAuthCode(), redirectUrl, _authType);
    }

    /**
     * 生成 URL 授权链接
     *
     * @param redirectUrl 回调地址
     * @param authType    授权模式 1.公众号展示, 2.小程序展示, 3.公众号和小程序都展示
     *
     * @return 授权链接
     */
    public String createComponentLoginPageByUrl(String redirectUrl, int... authType) {
        String url = "https://mp.weixin.qq.com/safe/bindcomponent?action=bindcomponent&no_scan=1" +
                "&component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=%s#wechat_redirect";
        int _authType = 3;
        if (authType != null && authType.length > 0) {
            _authType = authType[0];
        }
        return String.format(url, componentAppId, getPreAuthCode(), redirectUrl, _authType);
    }


}
