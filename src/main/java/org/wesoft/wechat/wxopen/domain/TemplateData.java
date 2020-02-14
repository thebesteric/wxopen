package org.wesoft.wechat.wxopen.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;

/**
 * 消息模板 - 数据
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class TemplateData extends HashMap<String, TemplateItem> {

    public static TemplateItem remark;

    public static TemplateData getInstance() {
        return new TemplateData();
    }

}
