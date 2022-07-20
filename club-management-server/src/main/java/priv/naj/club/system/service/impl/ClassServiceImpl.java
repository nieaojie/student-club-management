package priv.naj.club.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import priv.naj.club.common.execption.ParamException;
import priv.naj.club.system.controller.dto.ClassDTO;
import priv.naj.club.system.controller.dto.SpecialtyDTO;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.system.mapper.ClassMapper;
import priv.naj.club.system.service.ClassService;
import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.system.service.CollegeService;
import priv.naj.club.system.service.SpecialtyService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 班级表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class ClassServiceImpl extends CommonAbstractServiceImpl<ClassEntity, ClassMapper>
        implements ClassService {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private CollegeService collegeService;

    /**
     * 根据条件模糊查询
     * @param searchParam
     * @param page
     * @return
     */
    @Override
    public IPage<ClassDTO> searchByLike(String searchParam, Page<ClassEntity> page) {
        LambdaQueryWrapper<ClassEntity> wrapper = Wrappers.lambdaQuery(ClassEntity.class).eq(
                ClassEntity::getStatus, 1);
        wrapper.likeRight(ClassEntity::getName, searchParam).or()
               .likeRight(ClassEntity::getCreator, searchParam).or()
               .likeRight(ClassEntity::getUpdator, searchParam)
               .orderByDesc(ClassEntity::getUpdateTime);
        Page<ClassEntity> page1 = page(page, wrapper);
        List<ClassDTO> classDTOS = page1.getRecords().stream().map(classEntity -> {
            ClassDTO classDTO = new ClassDTO();
            SpecialtyDTO specialtyDTO = new SpecialtyDTO();
            SpecialtyEntity specialtyEntity = specialtyService.getById(classEntity.getSpecialtyPkid());
            BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
            CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
            specialtyDTO.setCollegeEntity(collegeEntity);
            BeanUtils.copyProperties(classEntity, classDTO);
            classDTO.setSpecialtyDTO(specialtyDTO);
            return classDTO;
        }).collect(Collectors.toList());
        Page<ClassDTO> page2 = new Page<>();
        BeanUtils.copyProperties(page1, page2);
        page2.setRecords(classDTOS);
        return page2;
    }

    /**
     * 重写-根据pkid查找
     * @param pkid
     * @return
     */
    @Override
    public ClassDTO infoQry2(String pkid) {
        if (pkid == null) {
            throw new ParamException("500", "id不能为空");
        }
        ClassEntity classEntity = getById(pkid);
        ClassDTO classDTO = new ClassDTO();
        BeanUtils.copyProperties(classEntity, classDTO);
        SpecialtyEntity specialtyEntity = specialtyService.getById(classEntity.getSpecialtyPkid());
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();
        BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
        CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
        specialtyDTO.setCollegeEntity(collegeEntity);
        classDTO.setSpecialtyDTO(specialtyDTO);
        return classDTO;
    }

    /**
     * 重写-分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ClassDTO> pageQry2(Integer pageNo, Integer pageSize) {
        Page<ClassEntity> page = page(new Page<>(pageNo, pageSize),
                                      new QueryWrapper<ClassEntity>().orderByDesc("update_time"));
        Page<ClassDTO> page2 = new Page<>();
        BeanUtils.copyProperties(page, page2);
        List<ClassDTO> classDTOS = page.getRecords().stream().map(classEntity -> {
            ClassDTO classDTO = new ClassDTO();
            BeanUtils.copyProperties(classEntity, classDTO);
            SpecialtyDTO specialtyDTO = new SpecialtyDTO();
            SpecialtyEntity specialtyEntity = specialtyService.getById(classEntity.getSpecialtyPkid());
            BeanUtils.copyProperties(specialtyEntity, specialtyDTO);
            CollegeEntity collegeEntity = collegeService.getById(specialtyEntity.getCollegePkid());
            specialtyDTO.setCollegeEntity(collegeEntity);
            classDTO.setSpecialtyDTO(specialtyDTO);
            return classDTO;
        }).collect(Collectors.toList());
        page2.setRecords(classDTOS);
        return page2;
    }
}
