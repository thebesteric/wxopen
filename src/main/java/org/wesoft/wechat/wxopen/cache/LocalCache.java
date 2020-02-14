package org.wesoft.wechat.wxopen.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LocalCache {

    private static final Logger logger = LoggerFactory.getLogger(LocalCache.class);

    // 默认的缓存容量
    private static final int DEFAULT_CAPACITY = 512;

    // 最大容量
    private static final int MAX_CAPACITY = 10000;

    // 是否开启监控
    private static final boolean ENABLE_MONITOR = false;

    // 刷新缓存的频率
    private static final int MONITOR_FREQUENCY = 5;

    // 构建本地缓存
    private static ConcurrentHashMap<String, CacheEntity> cache = new ConcurrentHashMap<>(DEFAULT_CAPACITY);

    private static LocalCache instance;

    public static LocalCache getInstance() {
        if (instance != null) {
            return instance;
        }
        return new LocalCache();
    }


    // 启动监控线程
    static {
        if (ENABLE_MONITOR) {
            new Thread(new ExpireMonitorThread()).start();
        }
    }

    static class ExpireMonitorThread implements Runnable {
        @Override
        public void run() {
            int errorTimes = 0;
            while (true) {
                try {
                    // logger.info("CACHE MONITOR START");
                    TimeUnit.SECONDS.sleep(MONITOR_FREQUENCY);
                    checkCache();
                    errorTimes = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorTimes++;
                    if (errorTimes >= 5) {
                        break;
                    }
                }
            }
        }
    }

    // 过期 key 剔除
    private static void checkCache() {
        cache.forEach((key, value) -> {
            checkCacheAndReturn(key);
        });
    }

    // 过期 key 剔除
    private static Object checkCacheAndReturn(String key) {
        CacheEntity cacheEntity = cache.get(key);
        if (cacheEntity.getExpire() != -1 && TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cacheEntity.getGmtCreate()) > cacheEntity.getExpire()) {
            cache.remove(key);
            logger.info("REMOVE EXPIRE KEY:{}", key);
            return null;
        }
        return cacheEntity.getValue();
    }

    /**
     * 将 key-value 保存到本地缓存并设置缓存过期时间
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间，如果是 -1 则表示永不过期
     *
     * @return boolean
     */
    public boolean put(String key, Object value, long expire) {
        if (cache.size() >= MAX_CAPACITY) {
            throw new RuntimeException("CAPACITY OVERFLOW");
        }
        return putCloneValue(key, value, expire);
    }

    /**
     * 将 key-value 保存到本地缓存
     *
     * @param key   key
     * @param value value
     *
     * @return boolean
     */
    public boolean put(String key, Object value) {
        return put(key, value, -1);
    }

    /**
     * 将值通过序列化 clone 处理后保存到缓存中，可以解决值引用的问题
     *
     * @param key        key
     * @param value      value
     * @param expireTime 过期时间，如果是-1 则表示永不过期
     *
     * @return boolean
     */
    private boolean putCloneValue(String key, Object value, long expireTime) {
        try {
            // 序列化赋值
            CacheEntity entityClone = clone(new CacheEntity(value, System.nanoTime(), expireTime));
            cache.put(key, entityClone);
            return true;
        } catch (Exception e) {
            logger.error("PUT VALUE HAS THROWS AN EXCEPTION:", e);
        }
        return false;
    }

    private <T extends Serializable> T clone(T obj) {
        T target = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            target = (T) ois.readObject();
            ois.close();
        } catch (Exception e) {
            logger.error("CLONE VALUE HAS THROWS AN EXCEPTION:", e);
        }
        return target;
    }

    /**
     * 根据 key 得到 value
     *
     * @param key key
     *
     * @return Object
     */
    public Object get(String key) {
        try {
            if (ENABLE_MONITOR) {
                return cache.get(key).getValue();
            }
            return checkCacheAndReturn(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 删除 key 对于的值
     *
     * @param key key
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * 清除缓存
     */
    public void clear() {
        cache.clear();
    }

    /**
     * 获取缓存长度
     *
     * @return int
     */
    public int size() {
        if (!ENABLE_MONITOR) {
            checkCache();
        }
        return cache.size();
    }
}
