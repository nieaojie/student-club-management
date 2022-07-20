package priv.naj.club.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.system.controller.dto.SpecialtyDTO;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 专业表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface SpecialtyService extends CommonAbstractService<SpecialtyEntity> {

    IPage<SpecialtyDTO> pageQry2(Integer pageNo, Integer pageSize);

    SpecialtyDTO infoQry2(String pkid);

    IPage<SpecialtyDTO> searchByLike(String searchParam, Page<SpecialtyEntity> page);
}
