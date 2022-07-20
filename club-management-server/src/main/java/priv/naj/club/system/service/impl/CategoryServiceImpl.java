package priv.naj.club.system.service.impl;

import java.util.Date;
import java.util.List;

import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.entity.CategoryEntity;
import priv.naj.club.system.mapper.CategoryMapper;
import priv.naj.club.system.service.CategoryService;
import priv.naj.club.common.CommonAbstractServiceImpl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class CategoryServiceImpl extends CommonAbstractServiceImpl<CategoryEntity, CategoryMapper>
        implements CategoryService {
    @Override
    public String createCmd(CategoryEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        String categoryName = entity.getCategoryName();
        Integer count = lambdaQuery().eq(CategoryEntity::getCategoryName, categoryName).count();
        if (count != 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "该分类已存在");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setCreateTime(new Date());
        entity.setPkid(uuid);
        entity.setCreator(WebContextHolder.getUsername());
        save(entity);
        return uuid;
    }

    /**
     * 模糊查询
     * @param searchParam
     * @param page
     * @return
     */
    @Override
    public IPage<CategoryEntity> searchByLike(String searchParam, Page<CategoryEntity> page) {
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(CategoryEntity::getCategoryName, searchParam).or()
               .likeRight(CategoryEntity::getCategoryCode, searchParam).or()
               .likeRight(CategoryEntity::getPkid, searchParam).or()
               .likeRight(CategoryEntity::getCreator, searchParam).or()
               .likeRight(CategoryEntity::getUpdator, searchParam)
               .orderByDesc(CategoryEntity::getUpdateTime);
        return super.page(page, wrapper);
    }
}
