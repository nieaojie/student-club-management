package priv.naj.club.system.service.impl;

import java.util.List;

import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.system.mapper.BpmMapper;
import priv.naj.club.system.service.BpmService;
import priv.naj.club.common.CommonAbstractServiceImpl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

/**
 * <p>
 * 流程表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class BpmServiceImpl extends CommonAbstractServiceImpl<BpmEntity, BpmMapper> implements BpmService {

    /**
     * 根据用户id查询用户所有加入社团的审核状态
     * @param userPkid
     * @return
     */
    @Override
    public List<BpmEntity> getBpmByUserId(String userPkid) {
        List<BpmEntity> list = lambdaQuery().eq(BpmEntity::getSubmitter, userPkid).list();
        return list;
    }


}
