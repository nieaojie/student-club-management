package priv.naj.club.common.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import priv.naj.club.common.enums.ClientType;

public class WebContext {
    private Locale locale;
    private String clientId;
    private String userPkid;
    private String account;
    private String username;
    private String realName;
    private String token;
    private ClientType clientType;
    private Map<String, String> custom = new HashMap();

    public WebContext() {
    }

    public String getCustom(String key) {
        return (String) this.custom.get(key);
    }

    public String putCustom(String key, String value) {
        return (String) this.custom.put(key, value);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getUserPkid() {
        return this.userPkid;
    }

    public String getAccount() {
        return this.account;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRealName() {
        return this.realName;
    }

    public String getToken() {
        return this.token;
    }

    public ClientType getClientType() {
        return this.clientType;
    }

    public Map<String, String> getCustom() {
        return this.custom;
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public void setUserPkid(final String userPkid) {
        this.userPkid = userPkid;
    }

    public void setAccount(final String account) {
        this.account = account;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setRealName(final String realName) {
        this.realName = realName;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public void setClientType(final ClientType clientType) {
        this.clientType = clientType;
    }

    public void setCustom(final Map<String, String> custom) {
        this.custom = custom;
    }
}
