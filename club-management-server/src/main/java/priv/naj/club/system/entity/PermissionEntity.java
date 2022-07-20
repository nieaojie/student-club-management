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
 * 权限表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_permission")
@ApiModel(value="PermissionEntity对象", description="权限表")
public class PermissionEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "parent id")
    private String parentId;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限url")
    private String path;

    @ApiModelProperty(value = "权限对应组件")
    private String component;

    @ApiModelProperty(value = "权限对应组件名称")
    private String componentName;

    @ApiModelProperty(value = "重定向")
    private String redirect;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型:0-一级菜单，1-子菜单")
    private Integer menuType;

    @ApiModelProperty(value = "是否显示:0-不显示，1-显示")
    private Integer isShow;

    @ApiModelProperty(value = "是否是叶节点，0-不是叶节点，1-是叶节点")
    private Integer isLeaf;

}
