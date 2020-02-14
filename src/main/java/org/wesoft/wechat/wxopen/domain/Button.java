package org.wesoft.wechat.wxopen.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 公众号菜单
 */
@Data
public class Button {

    private List<Menu> button = new ArrayList<>();

    public void setMenu(Button.Menu menu) {
        button.add(menu);
    }

    public Button.Menu getMenu(int index) {
        return button.get(index);
    }

    public List<Menu> getMenus() {
        return button;
    }

    @Data
    public static class Menu {

        /** 视图 */
        public static final String TYPE_VIEW = "view";
        /** 点击事件 */
        public static final String TYPE_CLICK = "click";
        /** 小程序 */
        public static final String TYPE_MINI_PROGRAM = "miniprogram";
        /** 扫码带提示 */
        public static final String TYPE_SCAN_CODE_WAIT_MSG = "scancode_waitmsg";
        /** 扫码推事件 */
        public static final String TYPE_SCAN_CODE_PUSH = "scancode_push";
        /** 系统拍照发图 */
        public static final String TYPE_PIC_SYS_PHOTO = "pic_sysphoto";
        /** 拍照或者相册发图 */
        public static final String TYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
        /** 微信相册发图 */
        public static final String TYPE_PIC_WEIXIN = "pic_weixin";
        /** 发送位置 */
        public static final String TYPE_LOCATION_SELECT = "location_select";
        /** 图片 */
        public static final String TYPE_MEDIA_ID = "media_id";
        /** 图文消息 */
        public static final String TYPE_VIEW_LIMITED = "view_limited";

        /** 类型 */
        private String type;

        /** 名称 */
        private String name;

        /** 响应动作 */
        private String key;

        /** 网页链接（当不支持小程序时，会打开此URL） */
        private String url;

        /** 小程序的appid */
        private String appid;

        /** 小程序的页面路径 */
        private String pagepath;

        /** 调用新增永久素材接口返回的合法 media_id */
        private String media_id;

        /** 二级菜单数组 */
        private List<Menu> sub_button = new ArrayList<>();

        public void setSubButton(Button.Menu menu) {
            sub_button.add(menu);
        }
    }

}
