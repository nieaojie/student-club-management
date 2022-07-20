package priv.naj.club.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.common.CommonAbstractController;
import priv.naj.club.system.controller.dto.ClassDTO;
import priv.naj.club.system.controller.dto.SpecialtyDTO;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.system.service.ClassService;

/**
 * <p>
 * 班级表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@RestController
@Api(tags = "班级表")
@RequestMapping("/system/classEntity")
public class ClassController extends CommonAbstractController<ClassEntity, ClassService> {

    @ApiOperation(
            value = "根据条件模糊查询",
            notes = "根据条件模糊查询"
    )
    @GetMapping("/searchByLike")
    public IPage<ClassDTO> searchByLike(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(name = "searchParam") String searchParam) {
        Page<ClassEntity> page = new Page<>(pageNo, pageSize);
        return service.searchByLike(searchParam, page);
    }

    @ApiOperation(
            value = "重写-根据pkid查找",
            notes = "重写-根据pkid查找"
    )
    @GetMapping({ "/info2" })
    public ClassDTO info2(@RequestParam("pkid") String pkid) {
        return service.infoQry2(pkid);
    }

    @ApiOperation(
            value = "重写-分页查询",
            notes = "重写-分页查询"
    )
    @GetMapping({ "/list2" })
    public IPage<ClassDTO> list2(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return service.pageQry2(pageNo, pageSize);
    }
}
