package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 消息模板 - 数据项
 */
@Data
@Accessors(chain = true)
public class TemplateItem implements Serializable {

    /** 内容 */
    private Object value;

    /** 模板内容字体颜色，不填默认为黑色 */
    private String color;

    public static TemplateItem newInstance() {
        return new TemplateItem();
    }

}
