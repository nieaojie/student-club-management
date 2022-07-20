package priv.naj.club.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import priv.naj.club.system.service.UserService;

@Order(3)
@Configuration
public class SecurityMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private List<UserContextPathPatternProvider> userContextPathPatternProviders;
    @Autowired
    private UserService userService;

    public SecurityMvcConfiguration() {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        UserContextInterceptor interceptor = new UserContextInterceptor(userService);
        InterceptorRegistration registration = registry.addInterceptor(interceptor);
        registration.excludePathPatterns(this.securityProperties.getExcludePathPatterns());
        this.userContextPathPatternProviders.forEach((provider) -> {
            registration.excludePathPatterns(provider.getExcludePathPatterns());
        });
    }
}
