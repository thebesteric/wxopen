package org.wesoft.wechat.wxopen.api;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.io.Serializable;
import java.util.*;

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
        Map<String, Object> _params = new HashMap<>();
        _params.put("content", content);
        Map<String, Object> params = getMessageParams(toUser, "text");
        params.put("text", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送图片消息
     *
     * @param toUser  接收者
     * @param mediaId mediaId
     */
    public JSONObject sendImage(String toUser, String mediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("media_id", mediaId);
        Map<String, Object> params = getMessageParams(toUser, "image");
        params.put("image", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送语音消息
     *
     * @param toUser  接收者
     * @param mediaId mediaId
     */
    public JSONObject sendVoice(String toUser, String mediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("media_id", mediaId);
        Map<String, Object> params = getMessageParams(toUser, "voice");
        params.put("voice", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送视频消息
     *
     * @param toUser       接收者
     * @param title        标题
     * @param description  描述
     * @param mediaId      mediaId
     * @param thumbMediaId 缩略图
     */
    public JSONObject sendVideo(String toUser, String title, String description, String mediaId, String thumbMediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("title", thumbMediaId);
        _params.put("description", description);
        _params.put("media_id", mediaId);
        _params.put("thumb_media_id", thumbMediaId);
        Map<String, Object> params = getMessageParams(toUser, "video");
        params.put("video", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送音乐消息
     *
     * @param toUser       接收者
     * @param title        标题
     * @param description  描述
     * @param musicUrl     音乐链接
     * @param hqMusicUrl   高品质音乐链接，wifi 环境优先使用该链接播放音乐
     * @param thumbMediaId 缩略图
     */
    public JSONObject sendMusic(String toUser, String title, String description, String musicUrl, String hqMusicUrl, String thumbMediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("title", title);
        _params.put("description", description);
        _params.put("musicUrl", musicUrl);
        _params.put("hqmusicurl", hqMusicUrl);
        _params.put("thumb_media_id", thumbMediaId);
        Map<String, Object> params = getMessageParams(toUser, "music");
        params.put("music", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送图文消息（点击跳转到外链）
     *
     * @param toUser      接收者
     * @param title       标题
     * @param description 描述
     * @param picUrl      图片地址
     * @param redirectUrl 跳转地址
     */
    public JSONObject sendNews(String toUser, String title, String description, String picUrl, String redirectUrl) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("title", title);
        _params.put("description", description);
        _params.put("url", redirectUrl);
        _params.put("picurl", picUrl);
        List<Map<String, Object>> list = Collections.singletonList(_params);
        Map<String, Object> params = getMessageParams(toUser, "news");
        params.put("news", list);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送图文消息
     *
     * @param toUser  接收者
     * @param mediaId mediaId
     */
    public JSONObject sendMpNews(String toUser, String mediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("media_id", mediaId);
        Map<String, Object> params = getMessageParams(toUser, "mpnews");
        params.put("mpnews", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送菜单消息
     * <p>
     * 当用户点击后，微信会发送一条XML消息到开发者服务器
     * <xml>
     * <ToUserName><![CDATA[ToUser]]></ToUserName>
     * <FromUserName><![CDATA[FromUser]]></FromUserName>
     * <CreateTime>1500000000</CreateTime>
     * <MsgType><![CDATA[text]]></MsgType>
     * <Content><![CDATA[满意]]></Content>
     * <MsgId>1234567890123456</MsgId>
     * <bizmsgmenuid>101</bizmsgmenuid>
     * </xml>
     *
     * @param toUser      接收者
     * @param headContent 提示头信息
     * @param tailContent 提示尾信息
     * @param list        菜单选项
     */
    public JSONObject sendMpMenu(String toUser, String headContent, String tailContent, List<MenuItem> list) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("head_content", headContent);
        _params.put("list", list);
        _params.put("tail_content", tailContent);
        Map<String, Object> params = getMessageParams(toUser, "msgmenu");
        params.put("mpnews", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送卡券
     * <p>
     * 特别注意客服消息接口投放卡券仅支持非自定义Code码和导入code模式的卡券的卡券
     *
     * @param toUser 接收者
     * @param cardId 微信卡卷编号
     */
    public JSONObject sendWxCard(String toUser, String cardId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("card_id", cardId);
        Map<String, Object> params = getMessageParams(toUser, "wxcard");
        params.put("wxcard", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    /**
     * 发送小程序卡片（要求小程序与公众号已关联）
     *
     * @param toUser       接收者
     * @param title        标题
     * @param appId        小程序的 appid，要求小程序的 appid 需要与公众号有关联关系
     * @param pagePath     小程序的页面路径，跟app.json对齐，支持参数，比如pages/index/index?foo=bar
     * @param thumbMediaId 缩略图
     */
    public JSONObject sendMiniProgramPage(String toUser, String title, String appId, String pagePath, String thumbMediaId) throws NullParameterException {
        Map<String, Object> _params = new HashMap<>();
        _params.put("title", title);
        _params.put("appid", appId);
        _params.put("pagepath", pagePath);
        _params.put("thumb_media_id", thumbMediaId);
        Map<String, Object> params = getMessageParams(toUser, "miniprogrampage");
        params.put("miniprogrampage", _params);
        return HttpUtils.doPost(getCustomerUrl(), params);
    }

    private String getCustomerUrl() throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
        return String.format(url, getAccessToken());
    }

    private Map<String, Object> getMessageParams(String toUser, String msgtype) {
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

    @Getter
    @Setter
    static class MenuItem implements Serializable {
        private String id;
        private String content;
    }

}
