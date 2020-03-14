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
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.crypt.AesException;
import org.wesoft.wechat.wxopen.client.crypt.WXBizMsgCrypt;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.domain.TemplateData;
import org.wesoft.wechat.wxopen.domain.TemplateItem;
import org.wesoft.wechat.wxopen.domain.TemplateMessage;
import org.wesoft.wechat.wxopen.domain.message.response.ResNewsMessage;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class WechatHelper extends WechatHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(WechatHelper.class);

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
        return WechatHelperSupport.appID;
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
        WechatHelperSupport.appID = appID;
        WechatHelperSupport.appSecret = appSecret;
        WechatHelperSupport.token = token;
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
        this.menu = new Menu();
        this.customer = new Customer();
        this.guide = new Guide();
        this.userInfo = new UserInfo();
        this.tag = new Tag();
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
        url = String.format(url, getAccessToken());
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
        url = String.format(url, getAccessToken());
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
    public void saveCustomQrCode(String ticket, String targetPath) throws Exception {
        String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";
        url = String.format(url, ticket);
        InputStream inputStream = HttpUtils.download(url);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
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
        url = String.format(url, getAccessToken());
        return HttpUtils.doPost(url, JSONObject.toJSONString(templateMessage));
    }


}
