package tjf.emuseum.emuseum.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 唐健峰
 * @version 1.0
 * @date 2023/4/17 14:53
 * @description:
 */
@Component
@Data
public class MinioProp {
    @Value("${minio.server.endpoint}")
    private String endpoint;
    /**
     * 用户名
     */
    @Value("${minio.server.accesskey}")
    private String accesskey;
    /**
     * 密码
     */
    @Value("${minio.server.secretkey}")
    private String secretkey;
    /**
     * 桶名
     */
    @Value("${minio.server.bucket}")
    private String bucket;
    /**
     * 路径
     */
    @Value("${minio.server.readpath}")
    private String readpath="/";
}
