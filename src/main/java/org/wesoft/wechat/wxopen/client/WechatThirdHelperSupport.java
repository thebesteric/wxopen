package org.wesoft.wechat.wxopen.client;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;
import org.wesoft.wechat.wxopen.constant.WechatConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class WechatThirdHelperSupport extends WechatHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(WechatThirdHelperSupport.class);

    public static String componentAppId, componentAppSecret, componentToken, componentVerifyTicket;

    public WXBizMsgCrypt crypt;

    /**
     * 解析 ComponentVerifyTicket
     */
    public String parseComponentVerifyTicket(HttpServletRequest request) throws Exception {
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");
        String msgSignature = request.getParameter("msg_signature");

        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        final StringBuilder buffer = new StringBuilder(1024);
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String postData = buffer.toString();
        // 消息解密
        String decryptMsg = crypt.decryptMsg(msgSignature, timestamp, nonce, postData);
        Map<String, String> messageMap = parseXml(decryptMsg);

        String infoType = messageMap.get("InfoType");
        if ("component_verify_ticket".equals(infoType)) {
            componentVerifyTicket = messageMap.get("ComponentVerifyTicket");
        }
        return componentVerifyTicket;
    }

    /**
     * 获取 ComponentAccessToken
     */
    public String getComponentAccessToken() {
        String componentAccessToken = (String) LocalCache.getInstance().get(WechatConstant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(componentAccessToken)) {
            String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
            JSONObject params = new JSONObject();
            params.put("component_appid", componentAppId);
            params.put("component_appsecret", componentAppSecret);
            params.put("component_verify_ticket", componentVerifyTicket);
            JSONObject ret = HttpUtils.doPost(url, params);
            if (ret != null) {
                logger.info(ret.toString());
                componentAccessToken = (String) ret.get(WechatConstant.COMPONENT_ACCESS_TOKEN);
                LocalCache.getInstance().put(WechatConstant.COMPONENT_ACCESS_TOKEN, componentAccessToken, ret.getLong(WechatConstant.EXPIRES_IN));
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
     * 获取 AuthorizationCode
     *
     * @param request request
     */
    public String getAuthorizationCode(HttpServletRequest request) {
        String authCode = request.getParameter("auth_code");
        int expiresIn = Integer.parseInt(request.getParameter("expires_in"));
        return authCode;
    }


}
