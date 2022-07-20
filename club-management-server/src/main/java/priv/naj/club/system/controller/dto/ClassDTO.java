package priv.naj.club.system.controller.dto;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractEntity;

/**
 * <p>
 * 班级表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "班级DTO")
public class ClassDTO extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级名称")
    private String name;

    @ApiModelProperty(value = "专业DTO")
    private SpecialtyDTO specialtyDTO;
}
