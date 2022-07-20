package priv.naj.club.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.common.CommonAbstractMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nieaojie
 */
public interface ClubMapper extends CommonAbstractMapper<ClubEntity> {
    IPage<ClubEntity> listClubWithCategoryPage(IPage<ClubEntity> page, @Param(Constants.WRAPPER) QueryWrapper<ClubEntity> wrapper);
}
