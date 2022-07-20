package priv.naj.club.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.system.mapper.RoleMapper;
import priv.naj.club.system.service.RoleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class RoleServiceImpl extends CommonAbstractServiceImpl<RoleEntity, RoleMapper> implements RoleService {
    @Override
    public String createCmd(RoleEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        String categoryName = entity.getRoleName();
        Integer count = lambdaQuery().eq(RoleEntity::getRoleName, categoryName).count();
        if (count != 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "该角色已存在");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setPkid(uuid);
        save(entity);
        return uuid;
    }

    @Override
    public IPage<RoleEntity> searchByLike(String searchParam, Page<RoleEntity> page) {
        LambdaQueryWrapper<RoleEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(RoleEntity::getRoleName, searchParam).or()
               .likeRight(RoleEntity::getRoleCode, searchParam).or()
               .likeRight(RoleEntity::getRoleDesc, searchParam).or()
               .likeRight(RoleEntity::getCreator, searchParam).or()
               .likeRight(RoleEntity::getUpdator, searchParam)
               .orderByDesc(RoleEntity::getUpdateTime);
        return super.page(page, wrapper);
    }

    @Override
    public boolean updateByIdCmd(RoleEntity entity) {
        boolean isExist = checkRoleIsExist(entity);
        if (isExist) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "角色名称或角色编码已存在");
        } else {
            return super.updateByIdCmd(entity);
        }
    }

    /**
     * 根据roleCode和roleName检查角色是否存在
     * @param roleEntity
     * @return true-存在，false-不存在
     */
    public boolean checkRoleIsExist(RoleEntity roleEntity) {
        Integer count = lambdaQuery().ne(RoleEntity::getPkid, roleEntity.getPkid()).and(
                wrapper -> wrapper.eq(RoleEntity::getRoleName, roleEntity.getRoleName()).or()
                                  .eq(RoleEntity::getRoleCode, roleEntity.getRoleCode())).count();
        return count > 0 ? true : false;
    }
}
