package cn.pridezh.rbac.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author PrideZH
 * @since 2022/8/20 14:09
 */
@Configuration
public class MinioConfig {

    // 服务地址
    @Value("${minio.url}")
    private String url;

    // 账号
    @Value("${minio.access}")
    private String access;

    // 密码
    @Value("${minio.secret}")
    private String secret;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(access, secret)
                .build();
    }

}
