package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 拍照发图的事件
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventMenuPicPhotoMessage extends BaseEventRequestMessage {

    /** 事件 KEY 值，设置的跳转 URL */
    private String EventKey;

    /** 发送的图片信息 */
    private SendPicsInfo SendPicsInfo;

    @Data
    public static class SendPicsInfo {

        /** 发送的图片数量 */
        private int Count;

        /** 图片的 MD5 值，开发者若需要，可用于验证接收到图片 */
        private List<String> PicMd5Sums = new ArrayList<>();

        public SendPicsInfo(int count, List<String> picMd5Sums) {
            Count = count;
            PicMd5Sums = picMd5Sums;
        }
    }
}
