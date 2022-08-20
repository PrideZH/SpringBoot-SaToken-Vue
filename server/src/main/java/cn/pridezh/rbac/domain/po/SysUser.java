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
@TableName
public class SysUser extends BasePO {

    private String username;

    private String password;

    private String nickname;

    private String avatarUrl;

    private Boolean superAdmin;

}