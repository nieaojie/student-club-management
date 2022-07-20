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
 * 班级表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_class")
@ApiModel(value = "ClassEntity对象", description = "班级表")
public class ClassEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "班级名称")
    private String name;

    @ApiModelProperty(value = "专业id")
    private String specialtyPkid;
}
