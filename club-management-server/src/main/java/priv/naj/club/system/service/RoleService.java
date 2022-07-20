package priv.naj.club.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nieaojie
 */
public interface RoleService extends CommonAbstractService<RoleEntity> {

    IPage<RoleEntity> searchByLike(String searchParam, Page<RoleEntity> page);
}
