/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-16 03:23:36
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-16 11:09:22
 */
package tjf.emuseum.emuseum.data.redis;

import java.util.Collection;
import java.util.Set;
import org.apache.ibatis.cache.CacheException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import tjf.emuseum.emuseum.config.RedisConfig;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component("redisCache")
public class RedisCache<K, V> {

    private String cacheName;

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    public V get(K k) throws CacheException {
        V v = (V) this.getRedisTemplate().opsForHash().get(this.cacheName, k.toString());
        return v;
    }

    public V put(K k, V v) throws CacheException {
        this.getRedisTemplate().opsForHash().put(this.cacheName, k.toString(), v);
        return v;
    }

    public V remove(K k) throws CacheException {
        return (V) this.getRedisTemplate().opsForHash().delete(this.cacheName, k.toString());
    }

    public void clear() throws CacheException {
        this.getRedisTemplate().delete(this.cacheName);
    }

    public int size() {
        return this.getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    public Set<K> keys() {
        return (Set<K>) this.getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    public Collection<V> values() {
        return (Collection<V>) this.getRedisTemplate().opsForHash().values(this.cacheName);
    }

    private RedisTemplate getRedisTemplate() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
        RedisTemplate redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
        return redisTemplate;
    }
}
