package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

/**
 * 小程序
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class MiniProgram extends BaseWechatDomain {

    /** 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系） */
    private String appid;

    /** 所需跳转到小程序的具体页面路径，支持带参数（示例：index?foo=bar） */
    private String pagepath;
}
