package priv.naj.club.system.service;

import java.util.List;

import priv.naj.club.system.controller.dto.NoticeDTO;
import priv.naj.club.system.entity.NoticeEntity;
import priv.naj.club.common.CommonAbstractService;

/**
 * <p>
 * 社团公告表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface NoticeService extends CommonAbstractService<NoticeEntity> {

    List<NoticeDTO> getClubAllNotice(String clubPkid,Integer status);
}
