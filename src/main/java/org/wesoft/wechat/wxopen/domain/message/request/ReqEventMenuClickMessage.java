package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

/**
 * 点击菜单拉取消息时的事件推送
 * <p>
 * 用户点击自定义菜单后，微信会把点击事件推送给开发者，请注意，点击菜单弹出子菜单，不会产生上报
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventMenuClickMessage extends BaseEventRequestMessage {

    /** 事件 KEY 值，与自定义菜单接口中 KEY 值对应 */
    private String EventKey;

    /** 指菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击了 */
    private String MenuID;

}
