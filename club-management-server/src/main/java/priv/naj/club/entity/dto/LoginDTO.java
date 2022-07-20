package priv.naj.club.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@ApiModel(description = "用户登录模型")
public class LoginDTO {
    @ApiModelProperty(value = "用户名")
    private String userUsername;
    @ApiModelProperty(value = "密码")
    private String userPassword;
    @ApiModelProperty(value = "当前用户角色")
    private String currentRole;
}
