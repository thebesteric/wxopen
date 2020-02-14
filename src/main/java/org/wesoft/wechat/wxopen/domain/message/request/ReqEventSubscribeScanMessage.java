package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

/**
 * 关注 / 取消关注事件
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventSubscribeScanMessage extends BaseEventRequestMessage {

    /** 事件KEY值，qrscene_为前缀，后面为二维码的参数值 */
    private String EventKey;

    /** 二维码的 ticket，可用来换取二维码图片 */
    private String Ticket;

}
