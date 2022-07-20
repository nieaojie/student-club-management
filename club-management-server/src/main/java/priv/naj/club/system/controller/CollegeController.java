package priv.naj.club.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.common.CommonAbstractController;
import priv.naj.club.system.controller.dto.CollegeAndSpecialtyTree;
import priv.naj.club.system.controller.dto.CollegeSpecialtyClassTree;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.service.CollegeService;

/**
 * <p>
 * 院系表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "学院表")
@RestController
@RequestMapping("/system/collegeEntity")
public class CollegeController extends CommonAbstractController<CollegeEntity, CollegeService> {
    @ApiOperation(
            value = "获取学院-专业-班级树形结构",
            notes = "获取学院-专业-班级树形结构"
    )
    @GetMapping("/getCollegeSpecialtyClassTree")
    public List<CollegeSpecialtyClassTree> getCollegeSpecialtyClassTree() {
        return service.getCollegeSpecialtyClassTree();
    }

    @ApiOperation(
            value = "根据条件模糊查询",
            notes = "根据条件模糊查询"
    )
    @GetMapping("/searchByLike")
    public IPage<CollegeEntity> searchByLike(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10")
                                                     Integer pageSize,
                                             @RequestParam(name = "searchParam") String searchParam) {
        Page<CollegeEntity> page = new Page<>(pageNo, pageSize);
        return service.searchByLike(searchParam, page);
    }

    @ApiOperation(
            value = "获取学院和专业树形结构",
            notes = "获取学院和专业树形结构"
    )
    @GetMapping("/getCollegeAndSpecialtyTree")
    public List<CollegeAndSpecialtyTree> getCollegeAndSpecialtyTree() {
        return service.getCollegeAndSpecialtyTree();
    }
}
