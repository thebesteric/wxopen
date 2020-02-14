package org.wesoft.wechat.wxopen.domain.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;

/**
 * 文本消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ResTextMessage extends BaseResponseMessage {

    /** 消息内容 */
    private String Content;

}
