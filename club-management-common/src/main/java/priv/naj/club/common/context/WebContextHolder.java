package priv.naj.club.common.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.util.Assert;

import priv.naj.club.common.enums.ClientType;

public class WebContextHolder {
    private static final ThreadLocal<WebContext> LOCAL = ThreadLocal.withInitial(WebContext::new);

    public WebContextHolder() {
    }

    public static void set(WebContext webContext) {
        Assert.notNull(webContext, "WebContext mush not null");
        LOCAL.set(webContext);
    }

    public static void clear() {
        setLocal((Locale) null);
        setClientId((String) null);
        setClientType((ClientType) null);
        setToken((String) null);
        setUserPkid((String) null);
        setUsername((String) null);
        setRealName((String) null);
        setAccount((String) null);
        setCustom(new HashMap());
    }

    public static void setLocal(Locale local) {
        ((WebContext) LOCAL.get()).setLocale(local);
    }

    public static Locale getLocale() {
        return ((WebContext) LOCAL.get()).getLocale();
    }

    public static String getClientId() {
        return ((WebContext) LOCAL.get()).getClientId();
    }

    public static void setAccount(String account) {
        ((WebContext) LOCAL.get()).setAccount(account);
    }

    public static void setToken(String token) {
        ((WebContext) LOCAL.get()).setToken(token);
    }

    public static String getUserPkid() {
        return ((WebContext) LOCAL.get()).getUserPkid();
    }

    public static String getAccount() {
        return ((WebContext) LOCAL.get()).getAccount();
    }

    public static String getUsername() {
        return ((WebContext) LOCAL.get()).getUsername();
    }

    public static String getRealName() {
        return ((WebContext) LOCAL.get()).getRealName();
    }

    public static void setRealName(String realName) {
        ((WebContext) LOCAL.get()).setRealName(realName);
    }

    public static String getToken() {
        return ((WebContext) LOCAL.get()).getToken();
    }

    public static void setCustom(Map<String, String> custom) {
        ((WebContext) LOCAL.get()).setCustom(custom);
    }

    public static Map<String, String> getCustom() {
        return ((WebContext) LOCAL.get()).getCustom();
    }

    public static void setUserPkid(String userPkid) {
        ((WebContext) LOCAL.get()).setUserPkid(userPkid);
    }

    public static void setLocale(Locale locale) {
        ((WebContext) LOCAL.get()).setLocale(locale);
    }

    public static void setClientId(String clientId) {
        ((WebContext) LOCAL.get()).setClientId(clientId);
    }

    public static void setUsername(String userName) {
        ((WebContext) LOCAL.get()).setUsername(userName);
    }

    public static void setClientType(ClientType clientType) {
        ((WebContext) LOCAL.get()).setClientType(clientType);
    }

    public static ClientType getClientType(ClientType clientType) {
        return ((WebContext) LOCAL.get()).getClientType();
    }

    public static String getCustom(String key) {
        return ((WebContext) LOCAL.get()).getCustom(key);
    }

    public static String putCustom(String key, String value) {
        return ((WebContext) LOCAL.get()).putCustom(key, value);
    }
}
