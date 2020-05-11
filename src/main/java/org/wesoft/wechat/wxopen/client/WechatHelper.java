package org.wesoft.wechat.wxopen.client;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.RegexUtils;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.api.*;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.crypt.AesException;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.domain.*;
import org.wesoft.wechat.wxopen.domain.message.response.ResNewsMessage;
import org.wesoft.wechat.wxopen.exception.NullParameterException;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class WechatHelper extends WechatHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(WechatHelper.class);

    private String appID, appSecret, token;

    /** 菜单模块 API */
    public Menu menu;

    /** 客服模块 API */
    public Customer customer;

    /** 导购模块 API */
    public Guide guide;

    /** 用户模块 API */
    public UserInfo userInfo;

    /** 标签模块 API */
    public Tag tag;

    /**
     * 获取 AppID
     */
    public String getAppId() {
        return this.appID;
    }

    /**
     * 帮助类
     *
     * @param appID        appID
     * @param appSecret    appSecret
     * @param token        token
     * @param encodeAESKey encodeAESKey
     */
    public WechatHelper(String appID, String appSecret, String token, String encodeAESKey) {
        this.appID = appID;
        this.appSecret = appSecret;
        this.token = token;
        if (StringUtils.isNotEmpty(encodeAESKey)) {
            try {
                this.crypt = new WXBizMsgCrypt(token, encodeAESKey, appID);
            } catch (AesException e) {
                e.printStackTrace();
            }
        }
        initApiComponent();
    }

    /**
     * API 组件初始化
     */
    private void initApiComponent() {
        this.menu = new Menu(appID, appSecret);
        this.customer = new Customer(appID, appSecret);
        this.guide = new Guide(appID, appSecret);
        this.userInfo = new UserInfo(appID, appSecret);
        this.tag = new Tag(appID, appSecret);
    }

    /**
     * 通过 code 换取网页授权 access_token
     *
     * @param code code 作为换取 access_token 的票据，每次用户授权带上的 code 将不一样，code 只能使用一次，5分钟未被使用自动过期
     * @return NetOAuthAccessToken
     */
    public NetOAuthAccessToken getWebAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, appID, appSecret, code);
        JSONObject jsonObject = HttpUtils.doGet(url);
        return jsonObject.toJavaObject(NetOAuthAccessToken.class);
    }

    /**
     * 响应消息对象转换成 XML 格式
     *
     * @param responseMessage 响应消息对象
     * @return xml
     */
    public String responseMessageToXml(BaseResponseMessage responseMessage) {
        xStream.alias("xml", responseMessage.getClass());
        return new String(xStream.toXML(responseMessage).trim().getBytes(), StandardCharsets.UTF_8);
    }

    /**
     * 图文消息对象转换成 XML 格式
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(ResNewsMessage newsMessage) {
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", ResNewsMessage.Article.class);
        return new String(StringUtils.trimBlank(xStream.toXML(newsMessage)).getBytes(), StandardCharsets.UTF_8);
    }

    /**
     * 新增永久素材
     *
     * @param file 素材
     */
    public JSONObject addMaterial(File file) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=image";
        url = String.format(url, getAccessToken(appID, appSecret));
        return HttpUtils.upload(url, "media", file);
    }

    /**
     * 生成二维码
     *
     * @param action    二维码类型: QR_LIMIT_STR_SCENE | QR_STR_SCENE
     * @param scene_str 场景值
     */
    private JSONObject generateQrCode(String action, String scene_str) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        JSONObject scene = new JSONObject();
        scene.put("scene_str", scene_str);
        JSONObject info = new JSONObject();
        info.put("scene", scene);
        JSONObject params = new JSONObject();
        params.put("action_name", action);
        params.put("action_info", info);
        return HttpUtils.doPost(url, params);
    }

    /**
     * 生成永久二维码
     *
     * @param scene 场景值
     */
    public JSONObject generateLimitQrCode(String scene) throws NullParameterException {
        return generateQrCode("QR_LIMIT_STR_SCENE", scene);
    }

    /**
     * 生成临时二维码
     *
     * @param scene 场景值
     */
    public JSONObject generateTempQrCode(String scene) throws NullParameterException {
        return generateQrCode("QR_STR_SCENE", scene);
    }

    /**
     * 保存自定义二维码
     *
     * @param ticket     ticket
     * @param targetPath 保存路径
     */
    public void saveCustomQrCodeByTicket(String ticket, String targetPath) throws IOException {
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";
        url = String.format(url, ticket);
        saveCustomQrCodeByUrl(url, targetPath);
    }

    /**
     * 保存自定义二维码
     *
     * @param base64Str  base64Str
     * @param targetPath 保存路径
     */
    public void saveCustomQrCodeByBase64Str(String base64Str, String targetPath) throws IOException {
        int index = base64Str.indexOf(",");
        if (index != -1) {
            base64Str = base64Str.substring(index + 1);
        }
        byte[] bytes = new BASE64Decoder().decodeBuffer(base64Str);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        writeToFile(inputStream, targetPath);
    }

    /**
     * 保存自定义二维码
     *
     * @param url        url
     * @param targetPath 保存路径
     */
    public void saveCustomQrCodeByUrl(String url, String targetPath) throws IOException {
        InputStream inputStream = HttpUtils.download(url);
        writeToFile(inputStream, targetPath);
    }

    private void writeToFile(InputStream inputStream, String targetPath) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int len; (len = inputStream.read(buffer)) != -1; ) {
            outStream.write(buffer, 0, len);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(targetPath));
        BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream);
        stream.write(outStream.toByteArray());
        stream.close();
        inputStream.close();
        outStream.close();
    }

    /**
     * 包装 TemplateMessage
     *
     * @param templateMessageId 模板ID
     * @param items             Map封装格式: {key:name, value:[String[0]:value, String[1]:color]}
     * @param url               跳转地址
     */
    public TemplateMessage packageTemplateMessage(String templateMessageId, Map<String, String[]> items,
                                                  String... url) {

        TemplateMessage templateMessage = TemplateMessage.newInstance(url)
                .setTemplate_id(StringUtils.trimBlank(templateMessageId));

        TemplateData templateData = TemplateData.getInstance();
        if (!items.isEmpty()) {
            for (String key : items.keySet()) {
                final boolean keyword = ITEM_KEY_FIRST.equals(key) || ITEM_KEY_REMARK.equals(key) || (key.length() >= 8
                        && key.startsWith(ITEM_KEY_KEYWORD) && RegexUtils.isNumber(key.substring(7)));
                if (keyword) {
                    TemplateItem item = TemplateItem.newInstance();
                    if (items.get(key) != null && items.get(key).length > 0) {
                        item.setValue(items.get(key)[0]);
                        if (items.get(key).length > 1) {
                            item.setColor(items.get(key)[1]);
                        }
                    }
                    templateData.put(key, item);
                }
            }
        }

        return templateMessage.setData(templateData);
    }

    /**
     * 发送模板消息
     *
     * @param templateMessage 模板消息
     */
    public JSONObject sendTemplateMessage(TemplateMessage templateMessage) throws NullParameterException {
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
        url = String.format(url, getAccessToken(appID, appSecret));
        return HttpUtils.doPost(url, JSONObject.toJSONString(templateMessage));
    }

    /**
     * 获取 JSAPI_TICKET
     *
     * @return String
     */
    public String getJsApiTicket() throws NullParameterException {
        String JsApiTicket = (String) LocalCache.getInstance().get(WechatConstant.JSAPI_TICKET);
        if (StringUtils.isEmpty(JsApiTicket)) {
            String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
            url = String.format(url, getAccessToken(appID, appSecret));
            JSONObject ret = HttpUtils.doGet(url);
            if (ret != null) {
                logger.info(ret.toString());
                JsApiTicket = (String) ret.get(WechatConstant.JSAPI_TICKET);
                LocalCache.getInstance().put(WechatConstant.JSAPI_TICKET, JsApiTicket, ret.getLong(WechatConstant.EXPIRES_IN));
            }
        }
        return JsApiTicket;
    }

    /**
     * 获取网页签名
     *
     * @param url 授权地址
     * @return Map<String, String>
     */
    public WebSignature webSignature(String url) throws NullParameterException {
        String nonce_str = UUID.randomUUID().toString();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String urlParam;
        String signature = "";
        // 注意这里参数名必须全部小写，且必须有序
        String jsApiTicket = this.getJsApiTicket();
        urlParam = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + nonce_str
                + "&timestamp=" + timestamp + "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(urlParam.getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> ret = new HashMap<>();
        ret.put("url", url);
        ret.put("appId", appID);
        ret.put("jsapi_ticket", jsApiTicket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        JSONObject jsonObject = new JSONObject(ret);
        return jsonObject.toJavaObject(WebSignature.class);
    }

    /**
     * 微信授权
     * <p>
     * 生成一个 URL 连接，如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE
     * code 作为换取 access_token 的票据
     *
     * @param redirectUri 回调 URL
     * @param scope       应用授权作用域 snsapi_base || snsapi_userinfo
     * @param state       重定向后的 state 参数
     * @return auth Url
     */
    public String webAuth(String redirectUri, String scope, String state) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
        return String.format(url, appID, redirectUri, scope, state);
    }

    /**
     * 通过 refreshToken 刷新 access_token
     *
     * @param refreshToken 用户刷新 access_token
     * @return NetOAuthAccessToken
     */
    public NetOAuthAccessToken webAccessTokenRefresh(String refreshToken) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
        url = String.format(url, appID, refreshToken);
        JSONObject jsonObject = HttpUtils.doGet(url);
        return jsonObject.toJavaObject(NetOAuthAccessToken.class);
    }

    /**
     * 检验授权凭证（access_token）是否有效
     *
     * @param accessToken accessToken
     * @param openid      openid
     */
    public BaseWechatDomain checkWebAccessToken(String accessToken, String openid) {
        String url = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
        url = String.format(url, accessToken, openid);
        JSONObject jsonObject = HttpUtils.doGet(url);
        return jsonObject.toJavaObject(BaseWechatDomain.class);
    }

    /**
     * 微信签名检查
     *
     * @param signature 签名
     * @param timestamp 时间戳
     * @param nonce     随机串
     * @return boolean
     */
    public boolean checkSignatures(String signature, String timestamp, String nonce) {
        this.signature = signature;
        this.timestamp = timestamp;
        this.nonce = nonce;

        String[] strArr = new String[]{nonce, token, timestamp};
        Arrays.sort(strArr);
        StringBuilder buffer = new StringBuilder();
        for (String string : strArr) {
            buffer.append(string);
        }
        return SHA1.encode(buffer.toString()).equals(signature);
    }

}
