package cn.pridezh.rbac.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author PrideZH
 * @since 2022/8/4 17:14
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // 允许跨域请求的域名
                .allowCredentials(true) // 是否允许证书 (cookies)
                .allowedMethods("*") // 允许方法
                .maxAge(3600); // 允许跨域时间
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射
        registry.addResourceHandler("/image/**").addResourceLocations("file:/home/file/image/");
    }

}