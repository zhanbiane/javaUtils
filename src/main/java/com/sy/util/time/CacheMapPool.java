package com.sy.util.time;

/**
 * @deccription TODO
 *
 * @author zhanbiane
 * 2018年7月12日
 */
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMapPool {
    private static CacheMapPool cachePool;
    private Map<Object, Object> cacheItems;
    private CacheMapPool() {
        cacheItems =new ConcurrentHashMap<Object, Object>();
    }
    /**
     * 获取唯一实例
     * @return instance
     */
    public static CacheMapPool getInstance() {
        if (cachePool ==null) {
            synchronized (CacheMapPool.class) {
                if (cachePool ==null) {
                    cachePool =new CacheMapPool();
                }
            }
        }
        return cachePool;
    }
    
    /**
     * 获取所有cache信息
     * @return cacheItems
     */
    public Map<Object, Object> getCacheItems() {
        return this.cacheItems;
    }
    
    /**
     * 清空cache
     */
    public void clearAllItems() {
        cacheItems.clear();
    }
    
    /**
     * 获取指定cache信息
     * @return cacheItem
     */
    public Object getCacheItem(Object key) {
        if (cacheItems.containsKey(key)) {
            return cacheItems.get(key);
        }
        return null;
    }
    
    /**
     * 存放cache信息
     */
    public void putCacheItem(Object key,Object value) {
        if (!cacheItems.containsKey(key)) {
            cacheItems.put(key, value);
        }
    }
    
    /**
     * 删除一个cache
     */
    public void removeCacheItem(Object key) {
        if (cacheItems.containsKey(key)) {
            cacheItems.remove(key);
        }
    }
    
    /**
     * 获取cache长度
     * @return size
     */
    public int getSize() {
        return cacheItems.size();
    }
    
}
