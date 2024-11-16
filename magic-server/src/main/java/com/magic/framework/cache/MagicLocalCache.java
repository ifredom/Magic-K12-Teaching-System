package com.magic.framework.cache;

import cn.hutool.cache.impl.LRUCache;


/**
 * 本地缓存实现类
 *  1. 使用LRUCache实现本地缓存
 * @param <T>
 */
public class MagicLocalCache<T>  {
    LRUCache<String, T> cache ;
    // 选择缓存算法
    private final String algorithm = "LRU";
    private int capacity = 1000;

    public MagicLocalCache(){
        cache = new LRUCache<>(capacity);
    }

    public MagicLocalCache(int capacity){
        cache = new LRUCache<>(capacity);
        this.capacity = capacity;
    }
    public MagicLocalCache(int capacity,long timeout){
        cache = new LRUCache<>(capacity,timeout);
        this.capacity = capacity;
    }
    public MagicLocalCache(int capacity,String algorithm){
        cache = new LRUCache<>(capacity);
        this.capacity = capacity;
    }

    public T get(String key) {
        return  cache.get(key);
    }
    public void put(String key, T value) {
        cache.put(key, value);
    }

}
