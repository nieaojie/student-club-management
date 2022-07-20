package priv.naj.club.system.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.controller.dto.NoticeDTO;
import priv.naj.club.system.entity.NoticeEntity;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.mapper.NoticeMapper;
import priv.naj.club.system.service.NoticeService;
import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.system.service.UserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

/**
 * <p>
 * 社团公告表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
public class NoticeServiceImpl extends CommonAbstractServiceImpl<NoticeEntity, NoticeMapper>
        implements NoticeService {
    @Autowired
    private UserService userService;

    /**
     * 查询社团公告
     * @param clubPkid
     * @param status
     * @return
     */
    @Override
    public List<NoticeDTO> getClubAllNotice(String clubPkid, Integer status) {
        LambdaQueryWrapper<NoticeEntity> wrapper = Wrappers.lambdaQuery(NoticeEntity.class).eq(
                NoticeEntity::getClubPkid, clubPkid);
        switch (status) {
            //未发布
            case 0:
                wrapper.eq(NoticeEntity::getStatus, 0);
                break;
            //已发布
            case 1:
                wrapper.eq(NoticeEntity::getStatus, 1);
                break;
            //全部
            default:
                break;
        }
        wrapper.orderByDesc(NoticeEntity::getCreateTime);
        List<NoticeDTO> list = super.list(wrapper).stream().map(
                entity -> {
                    NoticeDTO noticeDTO = new NoticeDTO();
                    BeanUtils.copyProperties(entity, noticeDTO);
                    UserEntity userEntity = userService.getById(entity.getUserPkid());
                    noticeDTO.setUserEntity(userEntity);
                    return noticeDTO;
                }).collect(Collectors.toList());
        return list;
    }

    @Override
    public String createCmd(NoticeEntity entity) {
        if (entity == null) {
            throw new BizException(ErrorCodeConst.PARAM);
        }
        entity.setUserPkid(WebContextHolder.getUserPkid());
        return super.createCmd(entity);
    }
}
