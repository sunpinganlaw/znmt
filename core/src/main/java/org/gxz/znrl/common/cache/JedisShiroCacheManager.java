package org.gxz.znrl.common.cache;

import org.gxz.znrl.common.redis.JedisManager;
import org.apache.shiro.cache.Cache;

/**
 * 这里的name是指自定义relm中的授权/认证的类名加上授权/认证英文名字
 *
 * @author vincent
 */
public class JedisShiroCacheManager implements ShiroCacheManager {

    private JedisManager jedisManager;

    @Override
    public <K, V> Cache<K, V> getCache(String name) {
        return new JedisShiroCache<K, V>(name, getJedisManager());
    }

    @Override
    public void destroy() {
        getJedisManager().getJedis().shutdown();
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
