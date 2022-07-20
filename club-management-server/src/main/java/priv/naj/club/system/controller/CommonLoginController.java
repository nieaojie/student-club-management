package priv.naj.club.system.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import priv.naj.club.common.config.redis.RedisUtil;
import priv.naj.club.common.consts.AuthCacheConstant;
import priv.naj.club.common.consts.AuthConstant;
import priv.naj.club.common.consts.AuthErrorCodeConstant;
import priv.naj.club.common.consts.RoleConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.util.PasswordUtil;
import priv.naj.club.entity.dto.LoginDTO;
import priv.naj.club.entity.dto.UserDTO;
import priv.naj.club.entity.req.RegisterReq;
import priv.naj.club.entity.res.LoginRes;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.service.UserService;

@RestController
@Api(tags = "用户登录注册")
@Slf4j
public class CommonLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 检查用户名是否可用
     */
    @ApiOperation(value = "检查用户名是否可用", notes = "检查用户名是否可用")
    @GetMapping("/userNameCanUse")
    public void userNameCanUse(String username){
        userService.userNameCanUse(username);
    }

    @ApiOperation(
            value = "登录",
            notes = "登录"
    )
    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUserUsername();
        String password = loginDTO.getUserPassword();
        //校验用户是否有效
        UserEntity userEntity = userService.getByUserName(username);
        userService.checkUserIsEffective(userEntity);
        //检验密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, userEntity.getUserSalt());
        String syspassword = userEntity.getUserPassword();
        if (!syspassword.equals(userpassword)) {
            throw new BizException(AuthErrorCodeConstant.PW_NOT_CORRECT, "用户名或密码不正确");
        }
        return userService.getLoginInfo(userEntity);
    }

    @ApiOperation(
            value = "注册",
            notes = "注册"
    )
    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterReq user) {
        String username = user.getUserUsername();
        String password = user.getUserPassword();
        //检验用户名是否可用
        userService.userNameCanUse(username);
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(user, entity);
        //设置加密密码
        String salt = PasswordUtil.randomGen(8);
        String encryptPwd = PasswordUtil.encrypt(username, password, salt);
        entity.setUserPassword(encryptPwd);
        entity.setUserSalt(salt);
        entity.setCreateTime(new Date());
        entity.setCreator(username);
        userService.save(entity);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(entity, userDTO);
        return userDTO;
    }

    @ApiOperation(
            value = "退出登录",
            notes = "退出登录"
    )
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader(AuthConstant.X_ACCESS_TOKEN);
        //用户退出逻辑
        log.info(" 用户名:  " + WebContextHolder.getUsername() + ",退出成功！ ");
        //清空用户登录Token缓存
        redisUtil.del(AuthCacheConstant.PREFIX_USER_TOKEN + token);
        //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
        redisUtil.del(
                String.format("%s::%s", AuthCacheConstant.SYS_USERS_CACHE, WebContextHolder.getUsername()));
        return "退出成功";
    }
}
