package cn.pridezh.rbac.service;

import cn.hutool.crypto.digest.DigestUtil;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author PrideZH
 * @since 2022/8/20 13:39
 */
@Slf4j
@Service
public class MinioService {

    @Value("${minio.bucket}")
    private String bucket;

    @Autowired
    private MinioClient minioClient;

    /**
     * 上传文件
     */
    public String putObject(MultipartFile file) throws Exception {
        String object = DigestUtil.sha256Hex(file.getInputStream());
        if (file.getOriginalFilename() != null) {
            object += file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") - 1);
        }
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        return object;
    }

    /**
     * 获取文件流
     */
    public GetObjectResponse getObject(String object) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
    }

    /**
     * 删除对象（文件）
     */
    public void removeObject(String object) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());
    }

}
