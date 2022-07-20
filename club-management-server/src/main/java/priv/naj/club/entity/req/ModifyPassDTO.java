package priv.naj.club.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("修改密码")
@Data
public class ModifyPassDTO {
    @ApiModelProperty(value = "旧密码")
    private String oldPass;
    @ApiModelProperty(value = "新密码")
    private String newPass;
}
