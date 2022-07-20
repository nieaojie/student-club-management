package priv.naj.club.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractService;
import priv.naj.club.entity.model.CommonLoginUser;
import priv.naj.club.entity.res.LoginRes;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.entity.UserEntity;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author nieaojie
 */
public interface UserService extends CommonAbstractService<UserEntity> {
    LoginRes getLoginInfo(UserEntity userEntity);

    UserEntity getByUserName(String username);

    void checkUserIsEffective(UserEntity userEntity);

    void userNameCanUse(String userUsername);

    CommonLoginUser getUserByToken(String token);

    IPage<UserDTO> listUserWithOtherInfo(Page<UserEntity> page);

    UserDTO infoCmd(String pkid);

    Boolean joinClub(String clubPkid);
}
