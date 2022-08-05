package cn.pridezh.rbac.service;

import cn.dev33.satoken.stp.StpInterface;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author PrideZH
 * @since 2022/8/3 20:30
 */
@AllArgsConstructor
@Component
public class StpInterfaceImpl implements StpInterface {

    private AuthService authService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return authService.access();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

}