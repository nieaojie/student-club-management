package priv.naj.club.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import priv.naj.club.common.CommonAbstractEntity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@ApiModel(value = "UserEntity对象", description = "用户表")
public class UserEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String userUsername;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "密码盐")
    private String userSalt;

    @ApiModelProperty(value = "真实姓名")
    private String userRealname;

    @ApiModelProperty(value = "邮箱")
    private String userEmail;

    @ApiModelProperty(value = "手机号")
    private String userPhone;

    @ApiModelProperty(value = "学号")
    private String userStudentNo;

    @ApiModelProperty(value = "学院")
    private String userCollege;

    @ApiModelProperty(value = "专业")
    private String userSpecialty;

    @ApiModelProperty(value = "年级")
    private String userGrade;

    @ApiModelProperty(value = "班级")
    private String userClass;

    @JSONField(format = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date userBirthday;

    @ApiModelProperty(value = "性别0-男，1-女")
    private Integer userGender;
}
