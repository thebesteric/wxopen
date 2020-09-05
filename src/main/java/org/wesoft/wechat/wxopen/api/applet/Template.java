package org.wesoft.wechat.wxopen.api.applet;

import com.alibaba.fastjson.JSONObject;
import org.omg.CORBA.OBJ_ADAPTER;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.client.applet.AppletHelperSupport;
import org.wesoft.wechat.wxopen.domain.applet.MpTemplateMsg;
import org.wesoft.wechat.wxopen.domain.applet.WeAppTemplateMsg;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板消息相关
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-12 18:24
 */
public class Template extends AppletHelperSupport {

    private String appID, appSecret;

    public Template(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
    }

    /**
     * 发送模板消息
     *
     * @param toUser           用户 openid
     * @param weAppTemplateMsg 小程序模板消息
     * @param mpTemplateMsg    公众号模板消息
     */
    public JSONObject send(String toUser, WeAppTemplateMsg weAppTemplateMsg, MpTemplateMsg mpTemplateMsg) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        JSONObject params = new JSONObject();
        params.put("touser", toUser);
        if (weAppTemplateMsg != null)
            params.put("weapp_template_msg", weAppTemplateMsg);
        params.put("mp_template_msg", mpTemplateMsg);
        return HttpUtils.doPost(url, params);
    }

    /**
     * 发送模板消息
     *
     * @param toUser        用户 openid
     * @param mpTemplateMsg 公众号模板消息
     */
    public JSONObject send(String toUser, MpTemplateMsg mpTemplateMsg) throws NullParameterException {
        return send(toUser, null, mpTemplateMsg);
    }

}
