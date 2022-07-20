package priv.naj.club.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.entity.PermissionTree;
import priv.naj.club.system.entity.PermissionEntity;
import priv.naj.club.system.service.PermissionService;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "权限表")
@RestController
@RequestMapping("/system/permissionEntity")
public class PermissionController extends CommonAbstractController<PermissionEntity, PermissionService> {
    @ApiOperation(
            value = "获得树形菜单列表",
            notes = "获得树形菜单列表"
    )
    @RequestMapping(value = "/getTreeList", method = RequestMethod.GET)
    public List<PermissionTree> list() {
        LambdaQueryWrapper<PermissionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionEntity::getIsDeleted, 0);
        List<PermissionEntity> permissionList = service.list(wrapper);
        List<PermissionTree> permissionTreeList = new ArrayList<>();
        service.getTreeList(permissionTreeList, permissionList, null);
        return permissionTreeList;
    }

    @ApiOperation(
            value = "查询子菜单",
            notes = "查询子菜单"
    )
    @RequestMapping(value = "/getSubmenu", method = RequestMethod.GET)
    public List<PermissionTree> getSystemSubmenu(@RequestParam("parentId") String parentId) {
        LambdaQueryWrapper<PermissionEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PermissionEntity::getParentId, parentId);
        List<PermissionEntity> permissionList = service.list(wrapper);
        List<PermissionTree> treeList = new ArrayList<>();
        for (PermissionEntity permission : permissionList) {
            PermissionTree tree = new PermissionTree(permission);
            treeList.add(tree);
        }
        return treeList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PermissionEntity add(@RequestBody PermissionEntity permission) {
        service.createCmd(permission);
        return null;
    }
}
