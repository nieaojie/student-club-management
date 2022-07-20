package priv.naj.club.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.entity.res.ClubResDto;
import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.system.service.RoleService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "角色表")
@RestController
@RequestMapping("/system/roleEntity")
public class RoleController extends CommonAbstractController<RoleEntity,RoleService> {
    @ApiOperation(
            value = "根据条件模糊查询",
            notes = "根据条件模糊查询"
    )
    @GetMapping("/searchByLike")
    public IPage<RoleEntity> searchByLike(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                          @RequestParam(name = "searchParam") String searchParam) {
        Page<RoleEntity> page = new Page<>(pageNo, pageSize);
        return service.searchByLike(searchParam, page);
    }
}
