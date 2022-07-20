package priv.naj.club.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractService;
import priv.naj.club.system.entity.CategoryEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nieaojie
 */
public interface CategoryService extends CommonAbstractService<CategoryEntity> {
    IPage<CategoryEntity> searchByLike(String searchParam, Page<CategoryEntity> page);
}
