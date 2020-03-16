package org.wesoft.wechat.wxopen.listener;

import org.wesoft.wechat.wxopen.client.WechatHelper;
import org.wesoft.wechat.wxopen.domain.message.request.*;
import org.wesoft.wechat.wxopen.exception.NullParameterException;

public abstract class WechatEvent {

    /** 文字事件 */
    public Object processText(WechatHelper helper, ReqTextMessage req) {
        return null;
    }

    /** 图片事件 */
    public Object processImage(WechatHelper helper, ReqImageMessage req) {
        return null;
    }

    /** 地理位置事件 */
    public Object processLocation(WechatHelper helper, ReqLocationMessage req) {
        return null;
    }

    /** 链接事件 */
    public Object processLink(WechatHelper helper, ReqLinkMessage req) {
        return null;
    }

    /** 音频事件 */
    public Object processVoice(WechatHelper helper, ReqVoiceMessage req) {
        return null;
    }

    /** 视频事件 */
    public Object processVideo(WechatHelper helper, ReqVideoMessage req) {
        return null;
    }

    /** 关注事件 */
    public Object processEventSubscribe(WechatHelper helper, ReqEventSubscribeMessage req) {
        return null;
    }

    /** 自定义二维码扫码关注事件 */
    public Object processEventSubscribeScan(WechatHelper helper, ReqEventSubscribeScanMessage req) {
        return null;
    }

    /** 已关注，扫码关注事件 */
    public Object processEventScan(WechatHelper helper, ReqEventSubscribeScanMessage req) {
        return null;
    }

    /** 取关事件 */
    public Object processEventUnSubscribe(WechatHelper helper, ReqEventSubscribeMessage req) {
        return null;
    }

    /** 菜单 - 点击事件 */
    public Object processEventMenuClick(WechatHelper helper, ReqEventMenuClickMessage req) {
        return null;
    }

    /** 菜单 - 跳转事件 */
    public Object processEventMenuView(WechatHelper helper, ReqEventMenuViewMessage req) {
        return null;
    }

    /** 菜单 - 扫码推事件的事件推送 */
    public Object processEventMenuScanCodePush(WechatHelper helper, ReqEventMenuScanCodeMessage req) {
        return null;
    }

    /** 菜单 - 扫码推事件且弹出“消息接收中”提示框的事件推送 */
    public Object processEventMenuScanCodeWaitMsg(WechatHelper helper, ReqEventMenuScanCodeMessage req) {
        return null;
    }

    /** 菜单 - 弹出系统拍照发图的事件推送 */
    public Object processEventMenuPicSysPhoto(WechatHelper helper, ReqEventMenuPicPhotoMessage req) {
        return null;
    }

    /** 菜单 - 弹出拍照或者相册发图的事件推送 */
    public Object processEventMenuPicPhotoOrAlbum(WechatHelper helper, ReqEventMenuPicPhotoMessage req) {
        return null;
    }

    /** 菜单 - 弹出微信相册发图器的事件推送 */
    public Object processEventMenuPicWeiXin(WechatHelper helper, ReqEventMenuPicPhotoMessage req) {
        return null;
    }

    /** 菜单 - 弹出微信相册发图器的事件推送 */
    public Object processEventMenuLocationSelect(WechatHelper helper, ReqEventMenuLocationSelectMessage req) {
        return null;
    }

    /** 菜单 - 点击菜单跳转小程序的事件推送 */
    public Object processEventMenuViewMiniProgram(WechatHelper helper, ReqEventMenuViewMiniProgramMessage req) {
        return null;
    }

    /** 地理位置推送事件 */
    public Object processEventLocation(WechatHelper helper, ReqEventLocationMessage req) {
        return null;
    }

}
