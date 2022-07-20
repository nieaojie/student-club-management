package priv.naj.club.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import priv.naj.club.common.CommonAbstractEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
@ApiModel(value="RoleEntity对象", description="")
public class RoleEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户角色名称")
    private String roleName;

    @ApiModelProperty(value = "用户角色编码")
    private String roleCode;

    @ApiModelProperty(value = "用户角色描述")
    private String roleDesc;

}
