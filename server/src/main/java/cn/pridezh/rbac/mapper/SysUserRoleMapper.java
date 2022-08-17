package cn.pridezh.rbac.mapper;

import cn.pridezh.rbac.domain.po.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;

/**
 * @author PrideZH
 * @since 2022-08-03
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 批量插入
     */
    Integer insertBatchSomeColumn(Collection<SysUserRole> entityList);

}
