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
import priv.naj.club.system.entity.CategoryEntity;
import priv.naj.club.system.service.CategoryService;

/**
 * @author nieaojie
 */
@Api(tags = "社团分类表")
@RestController
@RequestMapping("/system/categoryEntity")
public class CategoryController extends CommonAbstractController<CategoryEntity, CategoryService> {
    /**
     * 模糊查询，返回List
     * @param searchParam
     * @return
     */
    @ApiOperation(
            value = "根据条件模糊查询",
            notes = "根据条件模糊查询"
    )
    @GetMapping("/searchByLike")
    public IPage<CategoryEntity> searchByLike(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                              @RequestParam(name = "pageSize", defaultValue = "10")
                                                      Integer pageSize,
                                              @RequestParam String searchParam) {
        Page<CategoryEntity> page = new Page<>(pageNo, pageSize);
        return service.searchByLike(searchParam, page);
    }
}
