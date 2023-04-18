package tjf.emuseum.emuseum.data.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/18 17:14
 * @description:
 */
@Component
public class RedisCacheListener implements ApplicationListener<ContextClosedEvent> {
    @Autowired
    private RedisCache redisCache;
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        redisCache.destroy();
    }
}
