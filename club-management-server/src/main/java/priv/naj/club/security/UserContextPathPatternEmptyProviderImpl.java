package priv.naj.club.security;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class UserContextPathPatternEmptyProviderImpl implements UserContextPathPatternProvider {
    public UserContextPathPatternEmptyProviderImpl() {
    }

    @Override
    public List<String> getExcludePathPatterns() {
        return Lists.newArrayList(new String[]{ "/error"});
    }
}
