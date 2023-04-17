package tjf.emuseum.emuseum.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tjf.emuseum.emuseum.entity.MinioProp;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/17 15:00
 * @description:
 */
@Configuration
public class MinioConfig {
    @Autowired
    private MinioProp minioProp;
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .credentials(minioProp.getAccesskey(), minioProp.getSecretkey())
                .endpoint(minioProp.getEndpoint())
                .build();
    }
}
