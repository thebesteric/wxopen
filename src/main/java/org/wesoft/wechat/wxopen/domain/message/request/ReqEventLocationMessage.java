package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

/**
 * 上报地理位置事件
 * <p>
 * 用户同意上报地理位置后，每次进入公众号会话时，都会在进入时上报地理位置，
 * 或在进入会话后每5秒上报一次地理位置，公众号可以在公众平台网站中修改以上设置。
 * 上报地理位置时，微信会将上报地理位置事件推送到开发者填写的URL。
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventLocationMessage extends BaseEventRequestMessage {

    /** 地理位置纬度 */
    private String Latitude;

    /** 地理位置经度 */
    private String Longitude;

    /** 地理位置精度 */
    private String Precision;

}
