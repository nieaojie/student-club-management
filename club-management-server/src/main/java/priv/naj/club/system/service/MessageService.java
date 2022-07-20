package priv.naj.club.system.service;

import java.util.List;

import priv.naj.club.system.entity.MessageEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface MessageService extends CommonAbstractService<MessageEntity> {

    List<MessageEntity> searchUserMessages(Integer status);

    void markRead(String messagePkid);
}
