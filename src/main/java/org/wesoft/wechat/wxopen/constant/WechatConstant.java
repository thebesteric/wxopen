package org.wesoft.wechat.wxopen.constant;

public class WechatConstant {

    /** 三方平台 ACCESS_TOKEN */
    public static final String COMPONENT_ACCESS_TOKEN_PREFIX = "component_access_token_";

    /** 三方平台 PRE_AUTH_CODE */
    public static final String PRE_AUTH_CODE = "pre_auth_code";

    /** ACCESS_TOKEN */
    public static final String ACCESS_TOKEN_PREFIX = "access_token_";

    /** 小程序 ACCESS_TOKEN */
    public static final String APPLET_ACCESS_TOKEN_PREFIX = "applet_access_token_";

    /** JSAPI_TICKET */
    public static final String JSAPI_TICKET = "ticket";

    /** 过期时间 */
    public static final String EXPIRES_IN = "expires_in";

    /** 返回消息类型：文本 */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /** 返回消息类型：音乐 */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /** 返回消息类型：图文 */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /** 请求消息类型：文本 */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /** 请求消息类型：图片 */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /** 请求消息类型：链接 */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /** 请求消息类型：地理位置 */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /** 请求消息类型：音频 */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /** 请求消息类型：视频 */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /** 请求消息类型：小视频 */
    public static final String REQ_MESSAGE_TYPE_SHORT_VIDEO = "shortvideo";

    /** 请求消息类型：推送 */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /** 事件类型：subscribe（订阅） */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /** 事件类型：unsubscribe （取消订阅） */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /** 事件类型：click （自定义菜单 - 点击事件） */
    public static final String EVENT_TYPE_MENU_CLICK = "click";

    /** 事件类型：view （自定义菜单 - 点击事件） */
    public static final String EVENT_TYPE_MENU_VIEW = "view";

    /** 事件类型：scancode_push （自定义菜单 - 扫码推事件的事件推送） */
    public static final String EVENT_TYPE_MENU_SCANCODE_PUSH = "scancode_push";

    /** 事件类型：scancode_waitmsg （自定义菜单 - 扫码推事件且弹出 “消息接收中” 提示框的事件推送） */
    public static final String EVENT_TYPE_MENU_SCANCODE_WAIT_MSG = "scancode_waitmsg";

    /** 事件类型：pic_sysphoto （自定义菜单 - 弹出系统拍照发图的事件推送） */
    public static final String EVENT_TYPE_MENU_PIC_SYS_PHOTO = "pic_sysphoto";

    /** 事件类型：pic_photo_or_album （自定义菜单 - 弹出拍照或者相册发图的事件推送） */
    public static final String EVENT_TYPE_MENU_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";

    /** 事件类型：pic_weixin （自定义菜单 - 弹出微信相册发图器的事件推送） */
    public static final String EVENT_TYPE_MENU_PIC_WEIXIN = "pic_weixin";

    /** 事件类型：location_select （自定义菜单 - 弹出地理位置选择器的事件推送） */
    public static final String EVENT_TYPE_MENU_LOCATION_SELECT = "location_select";

    /** 事件类型：view_miniprogram （自定义菜单 - 点击菜单跳转小程序的事件推送） */
    public static final String EVENT_TYPE_MENU_VIEW_MINI_PROGRAM = "view_miniprogram";

    /** 事件类型：LOCATION （自动上报地理信息事件） */
    public static final String EVENT_TYPE_LOCATION = "location";

    /** 事件类型：SCAN （普通扫码） */
    public static final String EVENT_TYPE_SCAN = "scan";

    /** 模板项 - first */
    public static final String ITEM_KEY_FIRST = "first";

    /** 模板项 - keyword */
    public static final String ITEM_KEY_KEYWORD = "keyword";

    /** 模板项 - remark */
    public static final String ITEM_KEY_REMARK = "remark";

}
