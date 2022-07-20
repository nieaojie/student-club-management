package priv.naj.club.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.MessageStatusConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.entity.MessageEntity;
import priv.naj.club.system.mapper.MessageMapper;
import priv.naj.club.system.service.MessageService;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class MessageServiceImpl extends CommonAbstractServiceImpl<MessageEntity, MessageMapper>
        implements MessageService {

    /**
     * 标记已读
     * @param messagePkid
     */
    @Override
    public void markRead(String messagePkid) {
        //获取消息实体
        MessageEntity messageEntity = getById(messagePkid);
        if (messageEntity == null) {
            throw new BizException(ErrorCodeConst.NOT_FOUND, "消息不存在");
        }
        //修改消息的状态
        messageEntity.setStatus(MessageStatusConstant.READ.getValue());
        messageEntity.setReceiveTime(new Date());
        updateById(messageEntity);
    }

    /**
     * 查询当前用户的未读消息
     * @return
     * @param status
     */
    @Override
    public List<MessageEntity> searchUserMessages(Integer status) {
        LambdaQueryChainWrapper<MessageEntity> wrapper = lambdaQuery().like(MessageEntity::getReceiver,
                                                                            "%"+WebContextHolder.getUserPkid()+"%");
        switch (status) {
            case 0:
                wrapper.eq(MessageEntity::getStatus, MessageStatusConstant.UNREAD.getValue());
                break;
            case 1:
                wrapper.eq(MessageEntity::getStatus, MessageStatusConstant.READ.getValue());
                break;
            case 2:
                break;
            default:
                break;
        }
        return wrapper.list();
    }
}
