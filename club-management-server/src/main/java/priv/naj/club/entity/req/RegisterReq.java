package priv.naj.club.entity.req;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@ApiModel(description = "用户注册模型")
public class RegisterReq {
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
}
