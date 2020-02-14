package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class WebSignature extends BaseWechatDomain {

    /** APPID */
    private String appId;

    /** 网页授权 URL */
    private String url;

    /** jsapi_ticket */
    private String jsapi_ticket;

    /** 签名的随机串 */
    private String nonceStr;

    /** 签名时间戳 */
    private String timestamp;

    /** 签名 */
    private String signature;
}
