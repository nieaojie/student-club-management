package priv.naj.club.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "club.security")
public class SecurityProperties {
    private boolean enableTestToken;
    private List<String> excludePathPatterns = new ArrayList();

    public boolean isEnableTestToken() {
        return enableTestToken;
    }

    public void setEnableTestToken(boolean enableTestToken) {
        this.enableTestToken = enableTestToken;
    }

    public List<String> getExcludePathPatterns() {
        return excludePathPatterns;
    }

    public void setExcludePathPatterns(List<String> excludePathPatterns) {
        this.excludePathPatterns = excludePathPatterns;
    }
}
