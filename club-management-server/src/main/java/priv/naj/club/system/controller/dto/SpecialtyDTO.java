package priv.naj.club.system.controller.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;
import priv.naj.club.common.CommonAbstractEntity;
import priv.naj.club.system.entity.CollegeEntity;

/**
 * <p>
 * 专业表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "专业DTO")
public class SpecialtyDTO extends CommonAbstractDTO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "专业名称")
    private String name;

    @ApiModelProperty(value = "学院实体")
    private CollegeEntity collegeEntity;

}
