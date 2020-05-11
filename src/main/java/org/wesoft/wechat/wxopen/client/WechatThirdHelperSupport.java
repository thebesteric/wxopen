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

    public static String componentVerifyTicket;

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
