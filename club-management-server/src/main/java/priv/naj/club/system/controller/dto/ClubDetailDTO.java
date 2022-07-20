package priv.naj.club.system.controller.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import priv.naj.club.system.entity.CategoryEntity;
import priv.naj.club.system.entity.NoticeEntity;
import priv.naj.club.system.entity.UserEntity;

@Data
public class ClubDetailDTO {
    @ApiModelProperty(value = "pkid")
    private String pkid;

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

    @ApiModelProperty(value = "社团分类")
    private CategoryEntity categoryEntity;

    @ApiModelProperty(value = "社团海报")
    private String poster;

    @ApiModelProperty(value = "会长")
    private UserEntity chairman;

    @ApiModelProperty(value = "副会长")
    private List<UserEntity> viceChairman;

    @ApiModelProperty(value = "社团成立天数")
    private Integer createDays;

    @ApiModelProperty(value = "社团章程")
    private String clubRules;

    @ApiModelProperty(value = "社团公告")
    private List<NoticeDTO> notices;
}
