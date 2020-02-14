package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseEventRequestMessage;

/**
 * 扫码推事件
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqEventMenuScanCodeMessage extends BaseEventRequestMessage {

    /** 事件 KEY 值，设置的跳转 URL */
    private String EventKey;

    /** 扫描信息 */
    private ScanCodeInfo ScanCodeInfo;

    @Data
    public static class ScanCodeInfo {
        /** 扫描类型，一般是 qrcode */
        private String ScanType;

        /** 扫描结果，即二维码对应的字符串信息 */
        private String ScanResult;

        public ScanCodeInfo(String scanType, String scanResult) {
            ScanType = scanType;
            ScanResult = scanResult;
        }
    }

}
