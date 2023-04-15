/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 00:51:21
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 00:51:23
 */
package tjf.emuseum.emuseum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ RedisConfig.class, SecurityConfig.class })
public class SpringConfig {

}
