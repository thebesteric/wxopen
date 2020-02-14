package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

/**
 * AccessToken
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class AccessToken extends BaseWechatDomain {

    /** Access Token */
    private String access_token;

    /** 过期时间 */
    private Integer expires_in;
}
