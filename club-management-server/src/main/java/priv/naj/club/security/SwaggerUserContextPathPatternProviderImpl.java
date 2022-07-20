package priv.naj.club.security;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class SwaggerUserContextPathPatternProviderImpl implements UserContextPathPatternProvider{
    public SwaggerUserContextPathPatternProviderImpl() {
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return Lists.newArrayList(new String[]{ "/swagger-ui.html", "/js/**", "/css/**", "/images/**", "/lib/**", "/fonts/**", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"});
    }
}
