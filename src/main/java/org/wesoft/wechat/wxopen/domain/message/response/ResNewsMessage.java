package org.wesoft.wechat.wxopen.domain.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 图文消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ResNewsMessage extends BaseResponseMessage {

    /** 图文消息个数，限制为 10 条以内 */
    private int ArticleCount;

    /** 多条图文消息信息，默认第一个item为大图 */
    private List<Article> Articles = new ArrayList<>();

    /**
     * 图文（NewsMessage 会进行引用）
     */
    @Data
    public static class Article implements Serializable {

        /** 图文消息名称 */
        private String Title;

        /** 图文消息描述 */
        private String Description;

        /** 图片链接，支持 JPG、PNG 格式，较好的效果为大图640*320，小图 80*80，限制图片链接的域名需要与开发者填写的基本资料中的 Url一致 */
        private String PicUrl;

        /** 点击图文消息跳转链接 */
        private String Url;

    }

}
