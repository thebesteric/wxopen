package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

/**
 * 网页授权AccessToken
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class NetOAuthAccessToken extends BaseWechatDomain {

    /** Access Token */
    private String access_token;

    /** 过期时间 */
    private Integer expires_in;

    /** 过期时间 */
    private String refresh_token;

    /** 过期时间 */
    private String openid;

    /** 用户授权的作用域，使用逗号（,）分隔 */
    private String scope;

}
