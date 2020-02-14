package org.wesoft.wechat.wxopen.listener;

import org.wesoft.wechat.wxopen.client.WechatHelper;
import org.wesoft.wechat.wxopen.domain.message.request.*;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

public abstract class WechatEvent {

    /** 文字事件 */
    public String processText(WechatHelper wechatHelper, ReqTextMessage reqTextMessage) throws NullParameterException {
        return null;
    }

    /** 图片事件 */
    public String processImage(WechatHelper wechatHelper, ReqImageMessage reqImageMessage) {
        return null;
    };

    /** 地理位置事件 */
    public String processLocation(WechatHelper wechatHelper, ReqLocationMessage reqLocationMessage) {
        return null;
    };

    /** 链接事件 */
    public String processLink(WechatHelper wechatHelper, ReqLinkMessage reqLinkMessage) {
        return null;
    };

    /** 音频事件 */
    public String processVoice(WechatHelper wechatHelper, ReqVoiceMessage reqVoiceMessage) {
        return null;
    };

    /** 视频事件 */
    public String processVideo(WechatHelper wechatHelper, ReqVideoMessage reqVideoMessage) {
        return null;
    };

    /** 关注事件 */
    public String processEventSubscribe(WechatHelper wechatHelper, ReqEventSubscribeMessage reqEventSubscribeMessage) {
        return null;
    };

    /** 自定义二维码扫码关注事件 */
    public String processEventSubscribeScan(WechatHelper wechatHelper, ReqEventSubscribeScanMessage reqEventSubscribeScanMessage) {
        return null;
    };

    /** 已关注，扫码关注事件 */
    public String processEventScan(WechatHelper wechatHelper, ReqEventSubscribeScanMessage reqEventSubscribeScanMessage) {
        return null;
    };

    /** 取关事件 */
    public String processEventUnSubscribe(WechatHelper wechatHelper, ReqEventSubscribeMessage reqEventUnSubscribeMessage) {
        return null;
    };

    /** 菜单 - 点击事件 */
    public String processEventMenuClick(WechatHelper wechatHelper, ReqEventMenuClickMessage reqEventMenuClickMessage) {
        return null;
    };

    /** 菜单 - 跳转事件 */
    public String processEventMenuView(WechatHelper wechatHelper, ReqEventMenuViewMessage reqEventMenuViewMessage) {
        return null;
    };

    /** 菜单 - 扫码推事件的事件推送 */
    public String processEventMenuScanCodePush(WechatHelper wechatHelper, ReqEventMenuScanCodeMessage reqEventMenuScanCodePushMessage) {
        return null;
    };

    /** 菜单 - 扫码推事件且弹出“消息接收中”提示框的事件推送 */
    public String processEventMenuScanCodeWaitMsg(WechatHelper wechatHelper, ReqEventMenuScanCodeMessage reqEventMenuScanCodeWaitMessage) {
        return null;
    };

    /** 菜单 - 弹出系统拍照发图的事件推送 */
    public String processEventMenuPicSysPhoto(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicSysPhotoMessage) {
        return null;
    };

    /** 菜单 - 弹出拍照或者相册发图的事件推送 */
    public String processEventMenuPicPhotoOrAlbum(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicPhotoOrAlbumMessage) {
        return null;
    };

    /** 菜单 - 弹出微信相册发图器的事件推送 */
    public String processEventMenuPicWeiXin(WechatHelper wechatHelper, ReqEventMenuPicPhotoMessage reqEventMenuPicWeiXinMessage) {
        return null;
    };

    /** 菜单 - 弹出微信相册发图器的事件推送 */
    public String processEventMenuLocationSelect(WechatHelper wechatHelper, ReqEventMenuLocationSelectMessage reqEventMenuLocationSelectMessage) {
        return null;
    };

    /** 菜单 - 点击菜单跳转小程序的事件推送 */
    public String processEventMenuViewMiniProgram(WechatHelper wechatHelper, ReqEventMenuViewMiniProgramMessage reqEventMenuViewMiniProgramMessage) {
        return null;
    };

    /** 地理位置推送事件 */
    public String processEventLocation(WechatHelper wechatHelper, ReqEventLocationMessage reqEventLocationMessage) {
        return null;
    };

}
