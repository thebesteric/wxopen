package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 弹出地理位置选择器的事件推送
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventMenuLocationSelectMessage extends BaseEventRequestMessage {

    /** 事件 KEY 值，与自定义菜单接口中 KEY 值对应 */
    private String EventKey;

    /** 发送的位置信息 */
    private SendLocationInfo SendLocationInfo;

    @Data
    public static class SendLocationInfo {

        /** X坐标信息 */
        private String Location_X;

        /** Y坐标信息 */
        private String Location_Y;

        /** 精度，可理解为精度或者比例尺、越精细的话 scale越高 */
        private String Scale;

        /** 地理位置的字符串信息 */
        private String Label;

        /** 朋友圈POI的名字，可能为空 */
        private String Poiname;

        public SendLocationInfo(String Location_X, String Location_Y, String Scale, String Label, String Poiname) {
            this.Location_X = Location_X;
            this.Location_Y = Location_Y;
            this.Scale = Scale;
            this.Label = Label;
            this.Poiname = Poiname;
        }
    }

}
