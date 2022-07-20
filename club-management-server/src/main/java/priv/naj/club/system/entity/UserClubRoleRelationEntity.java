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
@TableName("user_club_role_relation")
@ApiModel(value="UserClubRoleRelationEntity对象", description="")
public class UserClubRoleRelationEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "社团id")
    private String clubId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

}
