package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 语音消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqVoiceMessage extends BaseRequestMessage {

    /** 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
    private String MediaId;

    /** 语音格式，如 amr, speex 等 */
    private String Format;

}
