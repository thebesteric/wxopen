package org.wesoft.wechat.wxopen.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.wesoft.wechat.wxopen.domain.message.request.ReqTextMessage;

import java.io.Serializable;

/**
 * 消息基类 Request（普通用户 -> 公众帐号）
 */
@Data
public class BaseRequestMessage implements Serializable {

    /** 开发者微信号 */
    private String ToUserName;

    /** 发送方帐号（一个OpenID） */
    private String FromUserName;

    /** 消息创建时间 （整型） */
    private long CreateTime;

    /** 消息类型（text/image/voice/video/shortvideo/location/link） */
    private String MsgType;

    /** 消息id，64位整型 */
    private long MsgId;

    public BaseRequestMessage() {
        super();
    }

    public BaseRequestMessage(String toUserName, String fromUserName, long createTime, String msgType, long msgId) {
        this.ToUserName = toUserName;
        this.FromUserName = fromUserName;
        this.CreateTime = createTime;
        this.MsgType = msgType;
        this.MsgId = msgId;
    }

    public BaseRequestMessage setBaseRequestMessage(BaseRequestMessage baseRequestMessage) {
        this.ToUserName = baseRequestMessage.getToUserName();
        this.FromUserName = baseRequestMessage.getFromUserName();
        this.CreateTime = baseRequestMessage.getCreateTime();
        this.MsgType = baseRequestMessage.getMsgType();
        this.MsgId = baseRequestMessage.getMsgId();
        return this;
    }
}
