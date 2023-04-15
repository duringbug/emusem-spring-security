/*
 * @Description: 
 * @Author: 唐健峰
 * @Date: 2023-04-15 00:40:32
 * @LastEditors: ${author}
 * @LastEditTime: 2023-04-15 17:49:28
 */
package tjf.emuseum.emuseum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@MapperScan("tjf.emuseum.emuseum.data.myBatis.mapper")
public class EmuseumApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmuseumApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOriginPatterns("*")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*")
						.exposedHeaders("Authorization")
						.allowCredentials(true).maxAge(3600);
			}
		};
	}
}
