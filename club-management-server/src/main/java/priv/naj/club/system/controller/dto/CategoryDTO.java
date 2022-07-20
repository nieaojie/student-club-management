package priv.naj.club.system.controller.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "分类DTO")
public class CategoryDTO extends CommonAbstractDTO {
    @ApiModelProperty(value = "社团分类名称")
    private String categoryName;

    @ApiModelProperty(value = "社团分类编码")
    private String categoryCode;

    @ApiModelProperty(value = "社团分类描述")
    private String categoryDesc;
}
