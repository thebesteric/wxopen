package org.wesoft.wechat.wxopen.domain.applet;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wesoft.wechat.wxopen.domain.TemplateData;
import org.wesoft.wechat.wxopen.domain.TemplateItem;

import java.io.Serializable;

/**
 * 小程序模板消息
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-12 18:29
 */
@Data
public class WeAppTemplateMsg implements Serializable {

    /** 小程序模板ID */
    private String template_id;

    /** 小程序页面路径 */
    private String page;

    /** 小程序模板消息 formid */
    private String form_id;

    /** 模板数据 */
    private TemplateData data;

    /** 小程序模板放大关键词 */
    private String emphasis_keyword;

    public WeAppTemplateMsg() {
        this.data = new TemplateData();
    }

    public WeAppTemplateMsg setDataItem(String key, String value) {
        data.put(key, new TemplateItem().setValue(value));
        return this;
    }

}
