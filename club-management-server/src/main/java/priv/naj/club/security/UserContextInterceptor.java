package priv.naj.club.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.entity.model.CommonLoginUser;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.system.service.UserService;

public class UserContextInterceptor extends HandlerInterceptorAdapter {
    private UserService userService;

    public UserContextInterceptor() {
    }

    public UserContextInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("X-Access-Token");
        if (token == null) {
            throw new BizException("token-1", "token为空");
        } else {
            CommonLoginUser user = userService.getUserByToken(token);
            if (user == null) {
                throw new BizException("token-2", "非法的token");
            } else {
                this.setUserContext(token, user);
                return true;
            }
        }
    }

    private void setUserContext(String token, CommonLoginUser user) {
        WebContextHolder.setToken(token);
        WebContextHolder.setUserPkid(user.getUserPkid());
        WebContextHolder.setUsername(user.getUsername());
        WebContextHolder.setRealName(user.getRealName());
        WebContextHolder.setClientId(user.getClientId());
        WebContextHolder.setClientType(user.getClientType());
    }
}
