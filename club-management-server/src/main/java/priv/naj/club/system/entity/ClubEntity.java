package priv.naj.club.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import priv.naj.club.common.CommonAbstractEntity;
import java.util.Date;
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
@TableName("sys_club")
@ApiModel(value="ClubEntity对象", description="")
public class ClubEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "社团类别id")
    private String clubCategoryId;

    @ApiModelProperty(value = "社团名称")
    private String clubName;

    @ApiModelProperty(value = "社团编码")
    private String clubCode;

    @ApiModelProperty(value = "社团简介")
    private String clubDesc;

    @ApiModelProperty(value = "社团成立时间")
    private Date clubCreateTime;

    @ApiModelProperty(value = "社团人数")
    private Integer clubPersonCount;

    @TableField(exist = false)
    @ApiModelProperty(value = "社团分类名称")
    private String categoryName;

    @ApiModelProperty(value = "社团海报")
    private String poster;

    @ApiModelProperty(value = "社团章程")
    private String clubRules;
}
