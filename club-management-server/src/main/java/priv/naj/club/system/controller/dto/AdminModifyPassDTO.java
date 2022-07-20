package priv.naj.club.system.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdminModifyPassDTO {
    @ApiModelProperty(value = "用户pkid")
    private String userPkid;

    @ApiModelProperty(value = "密码")
    private String userPassword;
}
