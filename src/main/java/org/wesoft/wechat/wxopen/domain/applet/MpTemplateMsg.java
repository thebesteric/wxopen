package org.wesoft.wechat.wxopen.domain.applet;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.wesoft.wechat.wxopen.domain.MiniProgram;
import org.wesoft.wechat.wxopen.domain.TemplateData;
import org.wesoft.wechat.wxopen.domain.TemplateItem;
import org.wesoft.wechat.wxopen.domain.TemplateMessage;

import java.io.Serializable;

/**
 * 微信模板消息
 *
 * @author Eric Joe
 * @version Ver 1.0
 * @build 2020-05-12 18:29
 */
@Data
public class MpTemplateMsg implements Serializable {

    /** 公众号appid，要求与小程序有绑定且同主体 */
    private String appid;

    /** 公众号模板 ID */
    private String template_id;

    /** 公众号模板消息所要跳转的 URL */
    private String url;

    /** 公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系 */
    private MiniProgram miniprogram;

    private TemplateData data;

    public MpTemplateMsg() {
        this.miniprogram = new MiniProgram();
        this.data = new TemplateData();
    }

    public MpTemplateMsg setDataItem(String key, String value, Color color) {
        this.data.put(key, new TemplateItem().setValue(value).setColor(color.toString()));
        return this;
    }

    public MpTemplateMsg setDataItem(String key, String value) {
        this.data.put(key, new TemplateItem().setValue(value));
        return this;
    }

    public MpTemplateMsg setMiniProgramAppId(String appid) {
        this.miniprogram.setAppid(appid);
        return this;
    }

    public MpTemplateMsg setMiniProgramPagePath(String pagePath) {
        this.miniprogram.setPagepath(pagePath);
        return this;
    }

}
