package priv.naj.club.system.service;

import java.util.List;

import priv.naj.club.entity.PermissionTree;
import priv.naj.club.system.entity.PermissionEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface PermissionService extends CommonAbstractService<PermissionEntity> {
    void getTreeList(List<PermissionTree> permissionTreeList, List<PermissionEntity> permissionList,
                     PermissionTree temp);
}
