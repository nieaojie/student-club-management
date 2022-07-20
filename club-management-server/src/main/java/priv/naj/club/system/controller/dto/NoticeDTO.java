package priv.naj.club.system.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;
import priv.naj.club.system.entity.UserEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "公告DTO")
public class NoticeDTO extends CommonAbstractDTO {
    @ApiModelProperty(value = "社团id")
    private String clubPkid;

    @ApiModelProperty(value = "发布人pkid")
    private UserEntity userEntity;

    @ApiModelProperty(value = "发布内容")
    private String content;
}
