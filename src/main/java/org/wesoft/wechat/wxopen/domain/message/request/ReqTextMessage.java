package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.*;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 文本消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqTextMessage extends BaseRequestMessage {

    /** 消息内容 */
    private String Content;

}

