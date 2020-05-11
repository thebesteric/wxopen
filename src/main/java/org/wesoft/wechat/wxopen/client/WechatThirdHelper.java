package org.wesoft.wechat.wxopen.client;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.crypt.AesException;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;
import org.wesoft.wechat.wxopen.constant.WechatConstant;

public class WechatThirdHelper extends WechatThirdHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(WechatThirdHelper.class);

    private String componentAppId, componentAppSecret, componentToken;

    public WechatThirdHelper(String componentAppId, String componentAppSecret, String componentToken, String componentEncodeAesKey) {
        this.componentAppId = componentAppId;
        this.componentAppSecret = componentAppSecret;
        this.componentToken = componentToken;
        if (StringUtils.isNotEmpty(componentEncodeAesKey)) {
            try {
                this.crypt = new WXBizMsgCrypt(componentToken, componentEncodeAesKey, componentAppId);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取 ComponentAccessToken
     */
    public String getComponentAccessToken() {
        String componentAccessToken = (String) LocalCache.getInstance().get(WechatConstant.COMPONENT_ACCESS_TOKEN_PREFIX + componentAppId);
        if (StringUtils.isEmpty(componentAccessToken)) {
            String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
            JSONObject params = new JSONObject();
            params.put("component_appid", componentAppId);
            params.put("component_appsecret", componentAppSecret);
            params.put("component_verify_ticket", componentVerifyTicket);
            JSONObject ret = HttpUtils.doPost(url, params);
            if (ret != null) {
                logger.info(ret.toString());
                componentAccessToken = (String) ret.get(WechatConstant.COMPONENT_ACCESS_TOKEN_PREFIX + componentAppId);
                LocalCache.getInstance().put(WechatConstant.COMPONENT_ACCESS_TOKEN_PREFIX + componentAppId, componentAccessToken, ret.getLong(WechatConstant.EXPIRES_IN));
            }
        }
        return componentAccessToken;
    }

    /**
     * 获取 PreAuthCode
     */
    public String getPreAuthCode() {
        String preAuthCode = (String) LocalCache.getInstance().get(WechatConstant.PRE_AUTH_CODE);
        if (StringUtils.isEmpty(preAuthCode)) {
            String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=%s";
            url = String.format(url, getComponentAccessToken());
            JSONObject params = new JSONObject();
            params.put("component_appid", componentAppId);
            JSONObject ret = HttpUtils.doPost(url, params);
            if (ret != null) {
                logger.info(ret.toString());
                preAuthCode = (String) ret.get(WechatConstant.PRE_AUTH_CODE);
                LocalCache.getInstance().put(WechatConstant.PRE_AUTH_CODE, preAuthCode, ret.getLong(WechatConstant.EXPIRES_IN));
            }
        }
        return preAuthCode;
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
