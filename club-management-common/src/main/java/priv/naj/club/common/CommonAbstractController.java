package priv.naj.club.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.ApiOperation;
import priv.naj.club.common.context.WebContextHolder;

public class CommonAbstractController<Entity extends CommonAbstractEntity, Service extends CommonAbstractService> {
    @Autowired
    protected Service service;

    public CommonAbstractController() {
    }

    @ApiOperation(
            value = "根据pkid查找",
            notes = "根据pkid查找"
    )
    @GetMapping({ "/info" })
    public Entity info(@RequestParam("pkid") String pkid) {
        return (Entity) service.infoQry(pkid);
    }

    @ApiOperation(
            value = "分页查询",
            notes = "分页查询"
    )
    @GetMapping({ "/list" })
    public IPage<Entity> list(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return this.service.pageQry(pageNo, pageSize);
    }

    @ApiOperation(
            value = "查询所有",
            notes = "查询所有"
    )
    @GetMapping({ "/listAll" })
    public List<Entity> listAll() {
        return service.listAll();
    }

    @ApiOperation(
            value = "添加",
            notes = "添加"
    )
    @PostMapping({ "/create" })
    public String create(@RequestBody Entity entity) {
        return service.createCmd(entity);
    }

    @ApiOperation(
            value = "批量添加",
            notes = "批量添加"
    )
    @PostMapping({ "/createBatch" })
    public Boolean createBatch(@RequestBody Collection<Entity> entityList) {
        return service.saveBatch(entityList);
    }

    @ApiOperation(
            value = "更新",
            notes = "更新"
    )
    @PostMapping({ "/update" })
    public Boolean update(@RequestBody Entity entity) {
        if (entity != null) {
            return service.updateByIdCmd(entity);
        }
        return null;
    }

    @ApiOperation(
            value = "通过id删除",
            notes = "通过id删除"
    )
    @GetMapping({ "/delete" })
    public void delete(@RequestParam("id") String id) {
        if (id != null) {
            service.removeById(id);
        }
    }

    @ApiOperation(
            value = "根据字段删除(单)-内部使用",
            notes = "根据字段删除-内部使用"
    )
    @PostMapping({ "/deleteByColumn" })
    public void deleteByColumn(@RequestParam String column, @RequestParam Object value) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq(column, value);
        this.service.remove(wrapper);
    }

    @ApiOperation(
            value = "通过id批量删除(逻辑删除)",
            notes = "通过id批量删除(逻辑删除)"
    )
    @GetMapping({ "/deleteByids" })
    public void deleteByPkids(@RequestParam("ids") List<String> ids) {
        if (ids != null) {
            service.removeByIds(ids);
        }
    }
}
