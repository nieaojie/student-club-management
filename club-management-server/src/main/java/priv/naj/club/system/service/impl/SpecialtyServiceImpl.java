package priv.naj.club.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.common.execption.ParamException;
import priv.naj.club.system.controller.dto.SpecialtyDTO;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.system.mapper.SpecialtyMapper;
import priv.naj.club.system.service.CollegeService;
import priv.naj.club.system.service.SpecialtyService;

/**
 * <p>
 * 专业表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class SpecialtyServiceImpl extends CommonAbstractServiceImpl<SpecialtyEntity, SpecialtyMapper>
        implements SpecialtyService {

    @Autowired
    private CollegeService collegeService;

    /**
     * 根据条件模糊查询
     * @param searchParam
     * @param page
     * @return
     */
    @Override
    public IPage<SpecialtyDTO> searchByLike(String searchParam, Page<SpecialtyEntity> page) {
        LambdaQueryWrapper<SpecialtyEntity> wrapper = Wrappers.lambdaQuery(SpecialtyEntity.class).eq(
                SpecialtyEntity::getStatus, 1);
        wrapper.likeRight(SpecialtyEntity::getName, searchParam).or()
               .likeRight(SpecialtyEntity::getCreator, searchParam).or()
               .likeRight(SpecialtyEntity::getUpdator, searchParam)
               .orderByDesc(SpecialtyEntity::getUpdateTime);
        Page<SpecialtyEntity> page1 = page(page, wrapper);
        List<SpecialtyDTO> specialtyDTOS = page1.getRecords().stream().map(specialtyEntity -> {
            SpecialtyDTO specialtyDTO = new SpecialtyDTO();
            BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
            CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
            specialtyDTO.setCollegeEntity(collegeEntity);
            return specialtyDTO;
        }).collect(Collectors.toList());
        Page<SpecialtyDTO> page2 = new Page<>();
        BeanUtils.copyProperties(page1, page2);
        page2.setRecords(specialtyDTOS);
        return page2;
    }

    /**
     * 重写-根据pkid查找
     * @param pkid
     * @return
     */
    @Override
    public SpecialtyDTO infoQry2(String pkid) {
        if (pkid == null) {
            throw new ParamException("500", "id不能为空");
        }
        SpecialtyEntity specialtyEntity = getById(pkid);
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
        CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
        specialtyDTO.setCollegeEntity(collegeEntity);
        return specialtyDTO;
    }

    /**
     * 重写-分页查询方法
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<SpecialtyDTO> pageQry2(Integer pageNo, Integer pageSize) {
        Page<SpecialtyEntity> page = page(new Page<>(pageNo, pageSize),
                                          new QueryWrapper<SpecialtyEntity>().orderByDesc("update_time"));
        Page<SpecialtyDTO> page2 = new Page<>();
        BeanUtils.copyProperties(page, page2);
        List<SpecialtyDTO> specialtyDTOS = page.getRecords().stream().map(specialtyEntity -> {
            SpecialtyDTO specialtyDTO = new SpecialtyDTO();
            BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
            CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
            specialtyDTO.setCollegeEntity(collegeEntity);
            return specialtyDTO;
        }).collect(Collectors.toList());
        page2.setRecords(specialtyDTOS);
        return page2;
    }
}
