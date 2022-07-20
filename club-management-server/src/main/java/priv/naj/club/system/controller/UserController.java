package priv.naj.club.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import priv.naj.club.common.CommonAbstractController;
import priv.naj.club.common.config.redis.RedisUtil;
import priv.naj.club.common.consts.AuthCacheConstant;
import priv.naj.club.common.consts.AuthErrorCodeConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.common.util.PasswordUtil;
import priv.naj.club.entity.req.ModifyPassDTO;
import priv.naj.club.system.controller.dto.AdminModifyPassDTO;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.service.UserService;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Slf4j
@Api(tags = "用户表")
@RestController
@RequestMapping("/system/userEntity")
public class UserController extends CommonAbstractController<UserEntity, UserService> {
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "请求加入社团", notes = "请求加入社团")
    @GetMapping("/joinClub")
    public Boolean joinClub(@RequestParam(value = "clubPkid") String clubPkid) {
        return service.joinClub(clubPkid);
    }

    @ApiOperation(value = "根据pkid查询用户详细信息", notes = "根据pkid查询用户详细信息")
    @GetMapping("/infoDetail")
    public UserDTO infoCmd(@RequestParam(value = "pkid") String pkid) {
        return service.infoCmd(pkid);
    }

    @ApiOperation(
            value = "用户修改密码",
            notes = "用户修改密码"
    )
    @PostMapping("/modifyPass")
    public void modifyPass(@RequestBody ModifyPassDTO dto) {
        String username = WebContextHolder.getUsername();
        UserEntity user = service.getByUserName(username);
        String userpassword = PasswordUtil.encrypt(username, dto.getOldPass(), user.getUserSalt());
        String syspassword = user.getUserPassword();
        if (!syspassword.equals(userpassword)) {
            throw new BizException(AuthErrorCodeConstant.PW_NOT_CORRECT, "原密码不正确");
        }
        String newEncrypt = PasswordUtil.encrypt(username, dto.getNewPass(), user.getUserSalt());
        user.setUserPassword(newEncrypt);
        service.update(user, new LambdaUpdateWrapper<>(UserEntity.class)
                .eq(UserEntity::getUserUsername, username));
        redisUtil.del(AuthCacheConstant.PREFIX_USER_TOKEN + WebContextHolder.getToken());
    }

    @ApiOperation(
            value = "管理员修改密码",
            notes = "管理员修改密码"
    )
    @PostMapping("/adminModifyPass")
    public void adminModifyPass(@RequestBody AdminModifyPassDTO dto) {
        System.out.println("userPkid:" + dto.getUserPkid());
        UserEntity user = service.getById(dto.getUserPkid());
        String newEncrypt = PasswordUtil.encrypt(user.getUserUsername(), dto.getUserPassword(),
                                                 user.getUserSalt());
        user.setUserPassword(newEncrypt);
        service.update(user, new LambdaUpdateWrapper<>(UserEntity.class)
                .eq(UserEntity::getUserUsername, user.getUserUsername())
                .eq(UserEntity::getPkid, dto.getUserPkid()));
    }

    @ApiOperation(
            value = "查询用户信息",
            notes = "查询用户信息"
    )
    @GetMapping(value = "/listUserWithOtherInfo")
    public IPage<UserDTO> listUserWithOtherInfo(
            @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        Page<UserEntity> page = new Page<>(pageNo, pageSize);
        return service.listUserWithOtherInfo(page);
    }

}
