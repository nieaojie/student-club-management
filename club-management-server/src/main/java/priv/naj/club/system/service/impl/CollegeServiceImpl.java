package priv.naj.club.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.controller.dto.CollegeAndSpecialtyTree;
import priv.naj.club.system.controller.dto.CollegeSpecialtyClassTree;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.system.mapper.CollegeMapper;
import priv.naj.club.system.service.ClassService;
import priv.naj.club.system.service.CollegeService;
import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.system.service.SpecialtyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 院系表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class CollegeServiceImpl extends CommonAbstractServiceImpl<CollegeEntity, CollegeMapper>
        implements CollegeService {

    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ClassService classService;

    /**
     * 获取学院-专业-班级树形结构
     * @return
     */
    @Override
    public List<CollegeSpecialtyClassTree> getCollegeSpecialtyClassTree() {
        //查询所有院系
        List<CollegeEntity> collegeEntityList = listAll();
        List<CollegeSpecialtyClassTree> treeList = collegeEntityList.stream().map(collegeEntity -> {
            //第一层：院系
            CollegeSpecialtyClassTree tree1 = new CollegeSpecialtyClassTree();
            tree1.setLabel(collegeEntity.getName());
            tree1.setValue(collegeEntity.getPkid());
            //获取该院系下的所有专业
            LambdaQueryWrapper<SpecialtyEntity> wrapper = Wrappers.lambdaQuery(SpecialtyEntity.class)
                                                                  .eq(SpecialtyEntity::getCollegePkid,
                                                                      collegeEntity.getPkid()).eq(
                            SpecialtyEntity::getStatus, 1);
            List<SpecialtyEntity> specialtyEntityList = specialtyService.list(wrapper);
            //判断其下有无专业，有-构建第二层树形结构，无-设置children为bull
            if (specialtyEntityList.size() > 0) {
                //有-构建第二层树形结构
                List<CollegeSpecialtyClassTree> treeList2 = specialtyEntityList.stream().map(
                        specialtyEntity -> {
                            CollegeSpecialtyClassTree tree2 = new CollegeSpecialtyClassTree();
                            tree2.setLabel(specialtyEntity.getName());
                            tree2.setValue(specialtyEntity.getPkid());
                            //获取班级
                            LambdaQueryWrapper<ClassEntity> wrapper2 = Wrappers.lambdaQuery(ClassEntity.class)
                                                                               .eq(
                                                                                       ClassEntity::getSpecialtyPkid,
                                                                                       specialtyEntity
                                                                                               .getPkid())
                                                                               .eq(ClassEntity::getStatus, 1);
                            List<ClassEntity> classEntityList = classService.list(wrapper2);
                            //判断专业下是否有班级，有-构建第二层树形结构，无-设置children为null
                            if (classEntityList.size() > 0) {
                                //有-构建第三层树形结构
                                List<CollegeSpecialtyClassTree> treeList3 = classEntityList.stream().map(
                                        classEntity -> {
                                            CollegeSpecialtyClassTree tree3 = new CollegeSpecialtyClassTree();
                                            tree3.setLabel(classEntity.getName());
                                            tree3.setValue(classEntity.getPkid());
                                            tree3.setChildren(null);
                                            return tree3;
                                        }).collect(Collectors.toList());
                                tree2.setChildren(treeList3);
                            } else {
                                //无-设置children为null
                                tree2.setDisabled(true);
                                tree2.setChildren(null);
                            }
                            return tree2;
                        }).collect(Collectors.toList());
                tree1.setChildren(treeList2);
            } else {
                tree1.setDisabled(true);
                tree1.setChildren(null);
            }
            return tree1;
        }).collect(Collectors.toList());
        return treeList;
    }

    /**
     * 获取学院和专业树形结构
     * @return
     */
    @Override
    public List<CollegeAndSpecialtyTree> getCollegeAndSpecialtyTree() {
        //查询所有院系
        List<CollegeEntity> collegeEntityList = listAll();
        List<CollegeAndSpecialtyTree> collegeAndSpecialtyTrees = collegeEntityList.stream().map(
                collegeEntity -> {
                    String collegeEntityPkid = collegeEntity.getPkid();
                    //获取该院系下的所有专业
                    LambdaQueryWrapper<SpecialtyEntity> wrapper = Wrappers.lambdaQuery(SpecialtyEntity.class)
                                                                          .eq(SpecialtyEntity::getCollegePkid,
                                                                              collegeEntityPkid).eq(
                                    SpecialtyEntity::getStatus, 1);
                    List<SpecialtyEntity> specialtyEntityList = specialtyService.list(wrapper);
                    //构建树形结构
                    CollegeAndSpecialtyTree tree = new CollegeAndSpecialtyTree();
                    tree.setLabel(collegeEntity.getName());
                    tree.setValue(collegeEntity.getPkid());
                    if (specialtyEntityList.size() > 0) {
                        List<CollegeAndSpecialtyTree> list = specialtyEntityList.stream().map(
                                specialtyEntity -> {
                                    CollegeAndSpecialtyTree tree2 = new CollegeAndSpecialtyTree();
                                    tree2.setLabel(specialtyEntity.getName());
                                    tree2.setValue(specialtyEntity.getPkid());
                                    tree2.setChildren(null);
                                    return tree2;
                                }).collect(Collectors.toList());
                        tree.setChildren(list);
                    } else {
                        tree.setDisabled(true);
                        tree.setChildren(null);
                    }
                    return tree;
                }).collect(Collectors.toList());
        return collegeAndSpecialtyTrees;
    }

    @Override
    public String createCmd(CollegeEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        Integer count = lambdaQuery().eq(CollegeEntity::getName, entity.getName()).count();
        if (count > 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "该名称已被使用");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setPkid(uuid);
        save(entity);
        return uuid;
    }

    @Override
    public IPage<CollegeEntity> searchByLike(String searchParam, Page<CollegeEntity> page) {
        LambdaQueryWrapper<CollegeEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.likeRight(CollegeEntity::getName, searchParam).or()
               .likeRight(CollegeEntity::getCreator, searchParam).or()
               .likeRight(CollegeEntity::getUpdator, searchParam)
               .orderByDesc(CollegeEntity::getUpdateTime);
        return super.page(page, wrapper);
    }

    @Override
    public List<CollegeEntity> listAll() {
        return lambdaQuery().eq(CollegeEntity::getStatus, 1).orderByDesc(CollegeEntity::getUpdateTime).list();
    }
}
