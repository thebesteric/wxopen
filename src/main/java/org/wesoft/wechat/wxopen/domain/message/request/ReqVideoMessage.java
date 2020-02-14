package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 视频消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqVideoMessage extends BaseRequestMessage {

    /** 视频消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
    private String MediaId;

    /** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 */
    private String ThumbMediaId;

}
