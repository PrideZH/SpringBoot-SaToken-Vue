package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.pridezh.rbac.convert.SysPermissionConvert;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.domain.vo.SysPermissionVO;
import cn.pridezh.rbac.service.SysPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022/8/4 21:54
 */
@Api(tags = "权限接口")
@AllArgsConstructor
@RestController
@RequestMapping("/sys-permission")
public class SysPermissionController {

    private SysPermissionService sysPermissionService;

    private SysPermissionConvert sysPermissionConvert;

    @ApiOperation(value = "获取所有权限")
    @SaCheckPermission("sys:permission:get")
    @GetMapping("")
    public Result<List<SysPermissionVO>> list() {
        return Result.success(sysPermissionConvert.toVOList(sysPermissionService.list()));
    }

}
