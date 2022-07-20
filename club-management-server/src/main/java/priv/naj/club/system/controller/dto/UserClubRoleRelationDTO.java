package priv.naj.club.system.controller.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;
import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.system.entity.RoleEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "用户社团角色DTO")
public class UserClubRoleRelationDTO extends CommonAbstractDTO {
    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "社团实体")
    private ClubEntity clubEntity;

    @ApiModelProperty(value = "角色实体")
    private RoleEntity roleEntity;

    @ApiModelProperty(value = "入会时间")
    private Date createTime;
}
