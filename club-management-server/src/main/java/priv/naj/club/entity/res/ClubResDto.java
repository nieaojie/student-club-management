package priv.naj.club.entity.res;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;
import priv.naj.club.common.CommonAbstractEntity;
import priv.naj.club.system.entity.CategoryEntity;
import priv.naj.club.system.entity.UserEntity;

@Data
@ToString(callSuper = true)
@ApiModel(description = "ClubResDto")
public class ClubResDto extends CommonAbstractEntity {
    @ApiModelProperty(value = "社团类别id")
    private String clubCategoryId;
    @ApiModelProperty(value = "社团名称")
    private String clubName;
    @ApiModelProperty(value = "社团编码")
    private String clubCode;
    @ApiModelProperty(value = "社团简介")
    private String clubDesc;
    @ApiModelProperty(value = "社团成立时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date clubCreateTime;
    @ApiModelProperty(value = "社团人数")
    private Integer clubPersonCount;
    @ApiModelProperty(value = "社团类别")
    private CategoryEntity clubCategoryEntity;
    @ApiModelProperty(value = "会长")
    private UserEntity chairman;
    @ApiModelProperty(value = "副会长")
    private List<UserEntity> viceChairman;
    @ApiModelProperty(value = "社团海报")
    private String poster;
    @ApiModelProperty(value = "社团章程")
    private String clubRules;
    @ApiModelProperty(value = "当前用户角色")
    private String currentRole;
}
