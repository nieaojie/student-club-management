package priv.naj.club.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.system.controller.dto.CollegeAndSpecialtyTree;
import priv.naj.club.system.controller.dto.CollegeSpecialtyClassTree;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 院系表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface CollegeService extends CommonAbstractService<CollegeEntity> {

    IPage<CollegeEntity> searchByLike(String searchParam, Page<CollegeEntity> page);

    List<CollegeAndSpecialtyTree> getCollegeAndSpecialtyTree();

    List<CollegeSpecialtyClassTree> getCollegeSpecialtyClassTree();
}
