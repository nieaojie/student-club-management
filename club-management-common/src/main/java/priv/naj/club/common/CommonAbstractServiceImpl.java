package priv.naj.club.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.common.execption.ParamException;
import priv.naj.club.common.util.UUIDGenerator;

public class CommonAbstractServiceImpl<Entity extends CommonAbstractEntity, M extends BaseMapper<Entity>>
        extends ServiceImpl<M, Entity> implements CommonAbstractService<Entity> {

    @Autowired
    protected UUIDGenerator uuidGenerator;
    @Autowired
    protected M mapper;

    @Override
    public String createCmd(Entity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setPkid(uuid);
        save(entity);
        return uuid;
    }

    @Override
    public Entity infoQry(String pkid) {
        if (pkid == null) {
            throw new ParamException("500", "id不能为空");
        }
        Entity entity = getById(pkid);
        if (entity != null) {
            return entity;
        }
        return null;
    }

    @Override
    public IPage<Entity> pageQry(Integer pageNo, Integer pageSize) {
        Page<Entity> page = new Page<>(pageNo, pageSize);
        return page(page, new QueryWrapper<Entity>().orderByDesc("update_time"));
    }

    @Override
    public List<Entity> listAll() {
        QueryWrapper<Entity> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        return list();
    }

    @Override
    public boolean updateByIdCmd(Entity entity) {
       return updateById(entity);
    }
}
