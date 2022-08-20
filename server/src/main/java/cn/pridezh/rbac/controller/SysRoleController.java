package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.pridezh.rbac.convert.SysRoleConvert;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.domain.dto.*;
import cn.pridezh.rbac.domain.vo.role.SysRoleItemVO;
import cn.pridezh.rbac.domain.vo.role.SysRoleVO;
import cn.pridezh.rbac.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@Api(tags = "角色接口")
@AllArgsConstructor
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {

    private SysRoleService sysRoleService;

    private SysRoleConvert sysRoleConvert;

    @ApiOperation(value = "创建角色")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "角色已存在")
    })
    @SaCheckPermission("sys:role:add")
    @PostMapping("")
    public Result<Void> post(@Validated @RequestBody SysRoleCreateDTO sysRoleCreateDTO) {
        sysRoleService.create(sysRoleCreateDTO);
        return Result.success(null);
    }

    @ApiOperation(value = "修改角色")
    @SaCheckPermission("sys:role:put")
    @PutMapping("/{id:\\d+}")
    public Result<Void> put(
            @PathVariable("id") String id,
            @Validated @RequestBody SysRoleUpdateDTO sysRoleUpdateDTO) {
        sysRoleUpdateDTO.setId(Long.valueOf(id));
        sysRoleService.update(sysRoleUpdateDTO);
        return Result.success(null);
    }

    @ApiOperation(value = "查询角色列表")
    @SaCheckPermission("sys:role:get")
    @GetMapping("/page")
    public Result<IPage<SysRoleItemVO>> listByPage(@Validated PageDTO pageDTO) {
        return Result.success(sysRoleService.page(pageDTO));
    }

    @ApiOperation(value = "获取所有角色")
    @SaCheckPermission("sys:role:get")
    @GetMapping("")
    public Result<List<SysRoleVO>> list() {
        return Result.success(sysRoleConvert.toVOList(sysRoleService.list()));
    }

    @ApiOperation(value = "删除角色")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "删除失败，该角色被使用")
    })
    @SaCheckPermission("sys:role:del")
    @DeleteMapping("/{id:\\d+}")
    public Result<Void> delete(@PathVariable("id") String id) {
        sysRoleService.deleteById(Long.valueOf(id));
        return Result.success(null);
    }

}
