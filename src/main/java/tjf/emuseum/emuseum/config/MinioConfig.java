package tjf.emuseum.emuseum.config;

import io.minio.MinioClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tjf.emuseum.emuseum.entity.MinioProp;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

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
    @Autowired
    OkHttpClient okHttpClient;

    @Bean
    public MinioClient minioClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        return MinioClient.builder()
                .credentials(minioProp.getAccesskey(), minioProp.getSecretkey())
                .endpoint(minioProp.getEndpoint())
                .httpClient(okHttpClient)//javaSpring与minio的https传输
                .build();
    }
}
