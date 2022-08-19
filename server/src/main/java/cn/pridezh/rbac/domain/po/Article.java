package cn.pridezh.rbac.domain.po;

import cn.pridezh.rbac.domain.common.BasePO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

 /**
 * @author PrideZH
 * @since 2022/8/5 12:20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName
public class Article extends BasePO {

    private String content;

}