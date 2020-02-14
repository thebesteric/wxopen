package org.wesoft.wechat.wxopen.domain.message.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.wesoft.wechat.wxopen.base.BaseResponseMessage;

/**
 * 音乐消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ResMusicMessage extends BaseResponseMessage {

	/** 音乐名称 */
    private String Title;
    
    /** 音乐描述 */
    private String Description;
    
    /** 音乐链接 */
    private String MusicUrl;
    
    /** 高质量音乐链接，WIFI环境优先使用该链接播放音乐 */
    private String HQMusicUrl;

}
