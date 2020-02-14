package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseWechatDomain;

/**
 * 消息模板
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class TemplateMessage extends BaseWechatDomain {

    /** 接收者 openid */
    private String touser;

    /** 模板 ID */
    private String template_id;

    /** 模板跳转链接 */
    private String url;

    /** 跳小程序所需数据，不需跳小程序可不用传该数据 */
    private MiniProgram miniprogram;

    /** 模板数据 */
    private TemplateData data;

    public static TemplateMessage newInstance(String... url) {
        TemplateMessage templateMessage = new TemplateMessage();
        if (url != null && url.length > 0) {
            templateMessage.setUrl(url[0]);
        }
        return templateMessage;
    }

    public static final class Color {

        /** 浅粉红 */
        public static final String LIGHT_PINK = "#FFB6C1";

        /** 粉红 */
        public static final String PINK = "#FFC0CB";

        /** 猩红 */
        public static final String CRIMSON = "#DC143C";

        /** 脸红的淡紫色 */
        public static final String LAVENDER_BLUSH = "#FFF0F5";

        /** 苍白的紫罗兰红色 */
        public static final String PALE_VIOLET_RED = "#DB7093";

        /** 热情的粉红 */
        public static final String HOT_PINK = "#FF69B4";

        /** 深粉色 */
        public static final String DEEP_PINK = "#FF1493";

        /** 适中的紫罗兰红色 */
        public static final String MEDIUM_VIOLET_RED = "#C71585";

        /** 兰花的紫色 */
        public static final String ORCHID = "#DA70D6";

        /** 蓟 */
        public static final String THISTLE = "#D8BFD8";

        /** 李子 */
        public static final String PLUM = "#DDA0DD";

        /** 紫罗兰 */
        public static final String VIOLET = "#EE82EE";

        /** 洋红 */
        public static final String MAGENTA = "#FF00FF";

        /** 纯蓝 */
        public static final String BLUE = "#0000FF";

        /** 适中的蓝色 */
        public static final String MEDIUM_BLUE = "#0000CD";

        /** 皇家蓝 */
        public static final String ROYAL_BLUE = "#4169E1";

        /** 紫色 */
        public static final String PURPLE = "#800080";

        /** 黑色 */
        public static final String BLACK = "#000000";

        /** 纯白 */
        public static final String WHITE = "#FFFFFF";

    }
}
