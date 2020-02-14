package org.wesoft.wechat.wxopen.domain.message.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.wesoft.wechat.wxopen.base.BaseRequestMessage;

/**
 * 链接消息
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Data
public class ReqLinkMessage extends BaseRequestMessage {

	/** 消息标题 */
	private String Title;
	
	/** 消息描述 */
	private String Description;
	
	/** 消息链接 */
	private String Url;

}
