package org.wesoft.wechat.wxopen.base;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息基类 Response（公众帐号 -> 普通用户）
 */
@Data
public class BaseResponseMessage implements Serializable {

	/** 接收方帐号（收到的OpenID）*/
    private String ToUserName;
    
    /** 开发者微信号 */
    private String FromUserName;
    
    /** 消息创建时间 （整型）*/
    private long CreateTime;
    
    /** 消息类型（text/music/news）*/
    private String MsgType;
    
    /** 位 0x0001 被标志时，星标刚收到的消息 */
    private int FuncFlag;

}
