package priv.naj.club.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import priv.naj.club.system.entity.PermissionEntity;

@Data
public class PermissionTree {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键pkid")
    private String pkid;
    @ApiModelProperty("状态, 0-未启用,1-启用")
    private Integer status;
    @TableLogic
    @ApiModelProperty("0 未删除, 1 删除")
    private Integer isDeleted;
    @ApiModelProperty("创建人")
    private String creator;
    @ApiModelProperty("创建时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    @ApiModelProperty("更新人")
    private String updator;
    @ApiModelProperty("更新时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;
    @ApiModelProperty("父Id")
    private String parentId;
    @ApiModelProperty("权限名称")
    private String name;
    @ApiModelProperty("权限路径")
    private String path;
    @ApiModelProperty("权限对应组件")
    private String component;
    @ApiModelProperty("权限对应组件名称")
    private String componentName;
    @ApiModelProperty("重定向路径")
    private String redirect;
    @ApiModelProperty("图标")
    private String icon;
    @ApiModelProperty("菜单类型:0-一级菜单，1-子菜单")
    private Integer menuType;
    @ApiModelProperty("是否显示:0-不显示，1-显示")
    private Integer isShow;
    @ApiModelProperty("是否是叶节点，0-不是叶节点，1-是叶节点")
    private Integer isLeaf;
    private List<PermissionTree> children;

    public PermissionTree(PermissionEntity permissionEntity) {
        this.menuType = permissionEntity.getMenuType();
        this.pkid = permissionEntity.getPkid();
        this.status = permissionEntity.getStatus();
        this.isDeleted = permissionEntity.getIsDeleted();
        this.creator = permissionEntity.getCreator();
        this.createTime = permissionEntity.getCreateTime();
        this.updator = permissionEntity.getUpdator();
        this.updateTime = permissionEntity.getUpdateTime();
        this.parentId = permissionEntity.getParentId();
        this.name = permissionEntity.getName();
        this.path = permissionEntity.getPath();
        this.component = permissionEntity.getComponent();
        this.componentName = permissionEntity.getComponentName();
        this.redirect = permissionEntity.getRedirect();
        this.icon = permissionEntity.getIcon();
        this.isShow = permissionEntity.getIsShow();
        this.isLeaf = permissionEntity.getIsLeaf();
        if (!(permissionEntity.getIsLeaf() == 0)) {
            this.children = new ArrayList<PermissionTree>();
        }
    }
}
