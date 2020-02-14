package org.wesoft.wechat.wxopen.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class CacheEntity implements Serializable {

    private Object value;

    private long gmtCreate;

    private long expire;

    public CacheEntity(Object value, long gmtCreate, long expire) {
        super();
        this.value = value;
        this.gmtCreate = gmtCreate;
        this.expire = expire;
    }
}
