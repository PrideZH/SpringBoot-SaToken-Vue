package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.exception.ServiceException;
import cn.pridezh.rbac.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/20 14:36
 */
@Api(tags = "文件上传接口")
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/upload")
public class UploadController {

    private MinioService minioService;

    private static final List<String> ALLOW_IMAGE_TYPE = List.of("image/jpeg", "image/png");

    @ApiOperation("上传用户头像")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "图片类型错误"),
            @ApiResponse(code = 1002, message = "图片大小超过500KB")
    })
    @SaCheckLogin
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> postAvatar(@NotNull(message = "缺少 file") MultipartFile file) throws Exception {
        if (!ALLOW_IMAGE_TYPE.contains(file.getContentType())) {
            throw new ServiceException(1001, "图片类型错误");
        }
        if (file.getSize() >= 500 * 1024) {
            throw new ServiceException(1002, "图片大小超过500KB");
        }
        return Result.success(minioService.putObject(file));
    }

}
