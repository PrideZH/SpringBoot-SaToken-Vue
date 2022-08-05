package cn.pridezh.rbac.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.pridezh.rbac.domain.common.Result;
import cn.pridezh.rbac.domain.dto.LoginDTO;
import cn.pridezh.rbac.domain.vo.user.SysUserInfoVO;
import cn.pridezh.rbac.domain.vo.TokenVO;
import cn.pridezh.rbac.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author PrideZH
 * @since 2022/8/3 20:10
 */
@Api(tags = "授权接口")
@AllArgsConstructor
@RestController
@RequestMapping("/sys-auth")
public class AuthController {

    private AuthService authService;

    @ApiOperation(value = "登录")
    @ApiResponses({
            @ApiResponse(code = 1001, message = "用户不存在"),
            @ApiResponse(code = 1002, message = "密码错误")
    })
    @PostMapping("/login")
    public Result<TokenVO> login(@RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/info")
    @SaCheckLogin
    public Result<SysUserInfoVO> info() {
        return Result.success(authService.info());
    }

    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    @SaCheckLogin
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.success(null);
    }

}
