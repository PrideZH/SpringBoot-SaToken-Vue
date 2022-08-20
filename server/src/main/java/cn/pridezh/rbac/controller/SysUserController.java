package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.domain.dto.PageDTO;
import cn.pridezh.rbac.domain.dto.SysUserCreateDTO;
import cn.pridezh.rbac.domain.dto.SysUserUpdateDTO;
import cn.pridezh.rbac.domain.vo.user.SysUserItemVO;
import cn.pridezh.rbac.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@Api(tags = "用户接口")
@AllArgsConstructor
@RestController
@RequestMapping("/sys-user")
public class SysUserController {

    private SysUserService sysUserService;

    @ApiOperation(value = "创建用户")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "用户名已存在")
    })
    @SaCheckPermission("sys:user:add")
    @PostMapping("")
    public Result<Void> post(@Validated @RequestBody SysUserCreateDTO sysUserCreateDTO) {
        sysUserService.create(sysUserCreateDTO);
        return Result.success(null);
    }

    @ApiOperation(value = "修改用户")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "不能修改当前用户")
    })
    @SaCheckPermission("sys:user:put")
    @PutMapping("/{id:\\d+}")
    public Result<Void> put(
            @PathVariable("id") String id,
            @Validated @RequestBody SysUserUpdateDTO sysUserUpdateDTO) {
        sysUserUpdateDTO.setId(Long.valueOf(id));
        sysUserService.update(sysUserUpdateDTO);
        return Result.success(null);
    }

    @ApiOperation(value = "查询用户列表")
    @SaCheckPermission("sys:user:get")
    @GetMapping("/page")
    public Result<IPage<SysUserItemVO>> listByPage(@Validated PageDTO pageDTO) {
        return Result.success(sysUserService.page(pageDTO));
    }

    @ApiOperation(value = "删除用户")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "不能删除当前用户"),
            @ApiResponse(code = 1002, message = "不能删除超级管理员")
    })
    @SaCheckPermission("sys:user:del")
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> delete(@PathVariable("id") String id) {
        sysUserService.deleteById(Long.valueOf(id));
        return Result.success(null);
    }

    @ApiOperation("上传用户头像")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "不能修改管理员头像"),
            @ApiResponse(code = 1002, message = "图片类型错误"),
            @ApiResponse(code = 1003, message = "图片大小超过500KB")
    })
    @SaCheckLogin
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<String> postAvatar(@NotNull(message = "缺少 file") MultipartFile file) throws Exception {
        return Result.success(sysUserService.updateAvatar(file));
    }

}
