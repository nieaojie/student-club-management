package priv.naj.club.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.system.controller.dto.ClassDTO;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 班级表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface ClassService extends CommonAbstractService<ClassEntity> {

    IPage<ClassDTO> pageQry2(Integer pageNo, Integer pageSize);

    ClassDTO infoQry2(String pkid);

    IPage<ClassDTO> searchByLike(String searchParam, Page<ClassEntity> page);
}
