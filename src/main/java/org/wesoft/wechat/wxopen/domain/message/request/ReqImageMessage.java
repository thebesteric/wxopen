package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 图片消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqImageMessage extends BaseRequestMessage {

    /** 图片链接 */
    private String PicUrl;

}
