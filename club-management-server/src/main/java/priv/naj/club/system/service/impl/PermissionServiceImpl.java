package priv.naj.club.system.service.impl;

import java.util.Date;
import java.util.List;

import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.entity.PermissionTree;
import priv.naj.club.system.entity.PermissionEntity;
import priv.naj.club.system.mapper.PermissionMapper;
import priv.naj.club.system.service.PermissionService;
import priv.naj.club.common.CommonAbstractServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class PermissionServiceImpl extends CommonAbstractServiceImpl<PermissionEntity, PermissionMapper> implements PermissionService {
    @Override
    public void getTreeList(List<PermissionTree> treeList, List<PermissionEntity> permissionList,
                            PermissionTree temp) {
        for (PermissionEntity permission : permissionList) {
            String parentId = permission.getParentId();
            PermissionTree tree = new PermissionTree(permission);
            if (temp == null && StringUtils.isEmpty(parentId)) {
                treeList.add(tree);
                if (tree.getIsLeaf() != 0) {
                    getTreeList(treeList, permissionList, tree);
                }
            } else if (temp != null && parentId != null && parentId.equals(temp.getPkid())) {
                temp.getChildren().add(tree);
                if (tree.getIsLeaf() != 0) {
                    getTreeList(treeList, permissionList, tree);
                }
            }
        }
    }

    @Override
    public String createCmd(PermissionEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        //判断是否是叶节点
        if (entity.getParentId() != null) {
            entity.setIsLeaf(0);
        } else {
            entity.setIsLeaf(1);
        }
        String uuid = uuidGenerator.newUUID();
        entity.setCreateTime(new Date());
        entity.setPkid(uuid);
        save(entity);
        return uuid;
    }
}
