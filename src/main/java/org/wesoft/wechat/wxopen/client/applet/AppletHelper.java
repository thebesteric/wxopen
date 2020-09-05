package org.wesoft.wechat.wxopen.client.applet;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wesoft.common.utils.StringUtils;
import org.wesoft.common.utils.web.HttpUtils;
import org.wesoft.wechat.wxopen.api.*;
import org.wesoft.wechat.wxopen.api.applet.Auth;
import org.wesoft.wechat.wxopen.api.applet.Template;
import org.wesoft.wechat.wxopen.cache.LocalCache;
import org.wesoft.wechat.wxopen.client.WechatHelperSupport;
import org.wesoft.wechat.wxopen.constant.WechatConstant;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

/**
 * 小程序帮助类
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-11 16:13
 */
@Getter
@Setter
public class AppletHelper extends AppletHelperSupport {

    private static final Logger logger = LoggerFactory.getLogger(AppletHelper.class);

    /** 认证相关 */
    public Auth auth;

    public Template template;

    /** 相关字段 */
    private String appID, appSecret;

    public AppletHelper(String appID, String appSecret) {
        this.appID = appID;
        this.appSecret = appSecret;
        initApiComponent();
    }

    /**
     * API 组件初始化
     */
    private void initApiComponent() {
        this.auth = new Auth(appID, appSecret);
        this.template = new Template(appID, appSecret);
    }


}
