package priv.naj.club.common;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CommonAbstractService<Entity extends CommonAbstractEntity>
        extends IService<Entity> {
    String createCmd(Entity entity);

    Entity infoQry(String pkid);

    IPage<Entity> pageQry(Integer pageNo,Integer pageSize);

    List<Entity> listAll();

    boolean updateByIdCmd(Entity entity);
}

