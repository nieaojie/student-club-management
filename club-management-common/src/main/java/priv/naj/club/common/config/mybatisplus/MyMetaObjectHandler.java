package priv.naj.club.common.config.mybatisplus;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
import priv.naj.club.common.context.WebContextHolder;

/**
 * @description: 自动填充功能
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
//        this.strictInsertFill(metaObject, "createTime", Date.class, new Date()); // 起始版本 3.3.0(推荐使用)
//        this.strictInsertFill(metaObject, "creator", String.class, WebContextHolder.getUsername()); // 起始版本 3.3.0(推荐使用)
        setFieldValByName("creator", WebContextHolder.getUsername(), metaObject);
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updator", WebContextHolder.getUsername(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
//        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date()); // 起始版本 3.3.0(推荐)
//        this.strictUpdateFill(metaObject, "updator",String.class,WebContextHolder.getUsername()); // 起始版本 3.3.3(推荐)
        setFieldValByName("updator", WebContextHolder.getUsername(), metaObject);
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
