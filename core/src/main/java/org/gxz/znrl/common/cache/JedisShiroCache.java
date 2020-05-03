package org.gxz.znrl.common.cache;

import org.gxz.znrl.common.redis.JedisManager;
import org.gxz.znrl.common.redis.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.*;

/**
 * redis shiro cache class
 *
 * @author vincent
 */
public class JedisShiroCache<K, V> implements Cache<K, V> {

    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    private static final int DB_INDEX = 1;

    private JedisManager jedisManager;

    private String name;

    public JedisShiroCache(String name, JedisManager jedisManager) {
        this.name = name;
        this.jedisManager = jedisManager;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(key));
        byte[] byteValue = new byte[0];
        try {
            byteValue = jedisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("get cache error");
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)),
                    SerializeUtil.serialize(value), -1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("put cache error");
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            jedisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(key)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("remove cache error");
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        //TODO
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        //TODO
        Set<byte[]> byteSet = jedisManager.getKeysByKeysPattern(SerializeUtil.serialize(this.REDIS_SHIRO_CACHE + "*"));
        Set<K> keys = new HashSet<K>();
        for (byte[] bs : byteSet) {
            keys.add((K) SerializeUtil.deserialize(bs));
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Set<byte[]> byteSet = jedisManager.getKeysByKeysPattern(SerializeUtil.serialize(this.REDIS_SHIRO_CACHE + "*"));
        List<V> result = new LinkedList<V>();
        for (byte[] bs : byteSet) {
            try {
                result.add((V) SerializeUtil.deserialize(jedisManager.getValueByKey(DB_INDEX, bs)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }

}
