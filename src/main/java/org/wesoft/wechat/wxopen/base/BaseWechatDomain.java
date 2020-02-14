package org.wesoft.wechat.wxopen.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseWechatDomain implements Serializable {

    /** 错误代码 */
    private Integer errcode = 0;

    /** 错误信息 */
    private String errmsg;

}
