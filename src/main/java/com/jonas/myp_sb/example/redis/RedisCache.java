package com.jonas.myp_sb.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"unchecked","rawtypes"})
@Component
public class RedisCache {

    @Autowired
    public RedisTemplate redisTemplate;

    //向Redis中存儲一個對象，可以指定過期時間
    public <T> void setCacheObject(final String key, final T value){
        redisTemplate.opsForValue().set(key,value);
    }

    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value, timeout, timeUnit);
    }

    //設置 key 的過期時間
    public boolean expire(final String key, final long timeout){
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit){
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    //從Redis中獲取一個對象
    public <T>T getCacheObject(final String key){
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    //刪除Redis中指定的key
    public boolean deleteObject(final String key){
        return redisTemplate.delete(key);
    }

    public long deleteObject(final Collection collection){
        return redisTemplate.delete(collection);
    }

    //向Redis中存儲一個List
    public <T> long setCacheList(final String key, final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count;
    }

    //從Redis中獲取一個List
    public <T> List<T> getCacheList(final String key){
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    //向Redis中存儲一個Set
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet){
        BoundSetOperations<String, T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()){
            setOperations.add(it.next());
        }
        return setOperations;
    }

    //從Redis中獲取一個Set
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }

    //向Redis中存儲一個Map
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap){
        if(dataMap != null){
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    //從Redis中獲取一個Map
    public <T> Map<String, T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }

    //向Redis中存儲一個Map中的值
    public <T> void setCacheMapValue(final String key, final String hKey, final T value){
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    //從Redis中獲取一個Map中的值
    public <T> T getCacheMapValue(final String key, final String hKey){
        HashOperations<String, String , T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    //刪除Redis中Map中指定的值
    public void delCacheMapValue(final String key, final String hKey){
        HashOperations opsForHash = redisTemplate.opsForHash();
        opsForHash.delete(key, hKey);
    }

    //從Redis中獲取一個Map中多個值
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys){
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    //指定的模式匹配獲取Redis中的key集合
    public Collection<String> keys(final String pattern){
        return redisTemplate.keys(pattern);
    }
}
