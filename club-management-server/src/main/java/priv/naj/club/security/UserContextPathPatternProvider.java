package priv.naj.club.security;

import java.util.List;

public interface UserContextPathPatternProvider {
    List<String> getExcludePathPatterns();
}
