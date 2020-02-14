package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

/**
 * JsApiTicket
 * 公众号用于调用微信 JS 接口的临时票据
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class JsApiTicket extends BaseWechatDomain {

    /** ticket */
    private String ticket;

    /** 过期时间 */
    private Integer expires_in;

}
