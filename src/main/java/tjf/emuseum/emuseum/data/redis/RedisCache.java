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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.connection.zset.Tuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import tjf.emuseum.emuseum.config.RedisConfig;

@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component("redisCache")
public class RedisCache<K, V> {
    @Autowired
    public RedisTemplate redisTemplate;

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
        V v = (V) redisTemplate.opsForHash().get(this.cacheName, k.toString());
        return v;
    }

    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForHash().put(this.cacheName, k.toString(), v);
        return v;
    }

    public V remove(K k) throws CacheException {
        return (V) redisTemplate.opsForHash().delete(this.cacheName, k.toString());
    }

    public void clear() throws CacheException {
        redisTemplate.delete(this.cacheName);
    }

    public int size() {
        return redisTemplate.opsForHash().size(this.cacheName).intValue();
    }

    public Set<K> keys() {
        return (Set<K>) redisTemplate.keys(this.cacheName);
    }

    public Collection<V> values() {
        return (Collection<V>) redisTemplate.opsForHash().values(this.cacheName);
    }
    public void setValue(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }
    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void destroy() {
        redisTemplate.getConnectionFactory().getConnection().close();
    }
}
