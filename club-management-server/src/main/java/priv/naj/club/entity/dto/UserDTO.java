package priv.naj.club.entity.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import priv.naj.club.common.CommonAbstractDTO;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@ApiModel(description = "用户字段")
public class UserDTO extends CommonAbstractDTO {
    @ApiModelProperty(value = "用户名")
    private String userUsername;
    @ApiModelProperty(value = "真实姓名")
    private String userRealname;
    @ApiModelProperty(value = "邮箱")
    private String userEmail;
    @ApiModelProperty(value = "手机号")
    private String userPhone;
    @ApiModelProperty(value = "学院")
    private String userCollege;
    @ApiModelProperty(value = "年级")
    private String userGrade;
    @ApiModelProperty(value = "班级")
    private String userClass;
    @ApiModelProperty(value = "生日")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date userBirthday;
    @ApiModelProperty(value = "性别0-男，1-女")
    private Integer userGender;
    @ApiModelProperty(value = "当前用户角色")
    private String currentRole;
}
