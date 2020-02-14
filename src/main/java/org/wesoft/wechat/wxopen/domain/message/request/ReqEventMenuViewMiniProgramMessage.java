package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

/**
 * 点击菜单跳转链接时的事件推送
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventMenuViewMiniProgramMessage extends BaseEventRequestMessage {

    /** 事件 KEY 值，设置的跳转 URL */
    private String EventKey;

    /** 菜单ID，如果是个性化菜单，则可以通过这个字段，知道是哪个规则的菜单被点击 */
    private String MenuId;

}
