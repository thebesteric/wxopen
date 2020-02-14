package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import org.wesoft.common.utils.HttpUtils;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.util.HashMap;
import java.util.Map;

/**
 * 客服消息 API
 */
public class Customer extends WechatHelperSupport {

    /**
     * 添加客服
     *
     * @param account  客服账号
     * @param nickname 客服昵称
     * @param password 客服密码
     */
    public JSONObject add(String account, String nickname, String password) throws NullParameterException {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=%s";
        url = String.format(url, getAccessToken());
        return HttpUtils.doPost(url, packageCustomer(account, nickname, password));
    }

    /**
     * 修改客服
     *
     * @param account  客服账号
     * @param nickname 客服昵称
     * @param password 客服密码
     */
    public JSONObject update(String account, String nickname, String password) throws NullParameterException {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=%s";
        url = String.format(url, getAccessToken());
        return HttpUtils.doPost(url, packageCustomer(account, nickname, password));
    }

    /**
     * 删除客服
     *
     * @param account  客服账号
     * @param nickname 客服昵称
     * @param password 客服密码
     */
    public JSONObject delete(String account, String nickname, String password) throws NullParameterException {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=%s";
        url = String.format(url, getAccessToken());
        return HttpUtils.doPost(url, packageCustomer(account, nickname, password));
    }

    /**
     * 获取所有客服账号
     */
    public JSONObject list() throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=%s";
        url = String.format(url, getAccessToken());
        return HttpUtils.doGet(url);
    }

    /**
     * 发送文本消息
     *
     * @param toUser  接收者
     * @param content 文本内容
     */
    public JSONObject sendText(String toUser, String content) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
        url = String.format(url, getAccessToken());
        Map<String, Object> params = getMessageParams(toUser, "text");
        Map<String, Object> _params = new HashMap<>();
        _params.put("content", content);
        params.put("text", _params);
        return HttpUtils.doPost(url, params);
    }

    public Map<String, Object> getMessageParams(String toUser, String msgtype) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("touser", toUser);
        params.put("msgtype", msgtype);
        return params;
    }

    private JSONObject packageCustomer(String account, String nickname, String password) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("kf_account", account);
        jsonObject.put("nickname", nickname);
        jsonObject.put("password", password);
        return jsonObject;
    }

}
