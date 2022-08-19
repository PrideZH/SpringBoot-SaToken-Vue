package cn.pridezh.rbac.domain.po;

import cn.pridezh.rbac.domain.common.BasePO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("sys_role__permission")
public class SysRolePermission extends BasePO {

    private Long roleId;

    private Long permissionId;

}