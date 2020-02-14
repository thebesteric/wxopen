package org.wesoft.wechat.wxopen.domain.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ResImageMessage extends BaseResponseMessage {

    private Image Image;

    @Data
    public static class Image implements Serializable {

        private String MediaId;

        public Image(String mediaId) {
            this.MediaId = mediaId;
        }
    }

}
