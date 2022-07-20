package priv.naj.club.system.controller.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;
import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.entity.SpecialtyEntity;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "用户DTO")
public class UserDTO extends CommonAbstractDTO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String userUsername;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "真实姓名")
    private String userRealname;

    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "学号")
    private String userStudentNo;

    @ApiModelProperty(value = "学院")
    private CollegeEntity userCollege;

    @ApiModelProperty(value = "专业")
    private SpecialtyEntity userSpecialty;

    @ApiModelProperty(value = "班级")
    private ClassEntity userClass;

    @ApiModelProperty(value = "年级")
    private String userGrade;

    @ApiModelProperty(value = "生日")
    private Date userBirthday;

    @ApiModelProperty(value = "性别0-男，1-女")
    private Integer userGender;

    @ApiModelProperty(value = "用户加入的社团和对应角色")
    private List<UserClubRoleRelationDTO> userClubRoleRelationDTOs;

    @ApiModelProperty(value = "社团审核信息")
    private List<BpmEntity> bpmEntity;
    @ApiModelProperty(value = "当前用户角色")
    private String currentRole;
}
