package priv.naj.club.system.service;

import java.util.List;

import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 流程表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface BpmService extends CommonAbstractService<BpmEntity> {

    List<BpmEntity> getBpmByUserId(String userPkid);
}
