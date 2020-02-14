package org.wesoft.wechat.wxopen.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 消息基类 Request（普通用户 -> 公众帐号）
 */
@Data
@AllArgsConstructor
public class BaseEventRequestMessage implements Serializable {

    /** 开发者微信号 */
    private String ToUserName;

    /** 发送方帐号（一个OpenID） */
    private String FromUserName;

    /** 消息创建时间 （整型） */
    private long CreateTime;

    /** 消息类型（event） */
    private String MsgType = "event";

    /** 事件类型，subscribe(订阅)、unsubscribe(取消订阅) */
    private String Event;

    public BaseEventRequestMessage() {
        super();
    }

    public BaseEventRequestMessage setBaseEventRequestMessage(BaseEventRequestMessage baseEventRequestMessage) {
        this.ToUserName = baseEventRequestMessage.getToUserName();
        this.FromUserName = baseEventRequestMessage.getFromUserName();
        this.CreateTime = baseEventRequestMessage.getCreateTime();
        this.MsgType = baseEventRequestMessage.getMsgType();
        this.Event = baseEventRequestMessage.getEvent();
        return this;
    }

}
