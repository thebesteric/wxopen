package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.domain.Button;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

/**
 * 菜单 API
 */
public class Menu extends WechatHelperSupport {

    private String appID, appSecret;

    public Menu(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
    }

    /**
     * 创建菜单
     *
     * @param button 菜单
     */
    public JSONObject createMenu(Button button) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        return HttpUtils.doPost(url, JSONObject.toJSONString(button));
    }

    /**
     * 获取菜单
     */
    public JSONObject getCurrentMenu() throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        return HttpUtils.doPost(url);
    }

    /**
     * 删除菜单
     */
    public JSONObject deleteMenu() throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        return HttpUtils.doPost(url);
    }

}
