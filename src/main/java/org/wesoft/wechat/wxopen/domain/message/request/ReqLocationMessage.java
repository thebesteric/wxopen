package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 地理位置消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqLocationMessage extends BaseRequestMessage {

    /** 地理位置维度 */
    private String Location_X;

    /** 地理位置经度 */
    private String Location_Y;

    /** 地图缩放大小 */
    private String Scale;

    /** 地理位置信息 */
    private String Label;

}
