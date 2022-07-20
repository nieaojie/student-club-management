package priv.naj.club.entity.model;

import io.swagger.annotations.ApiModelProperty;
import priv.naj.club.common.enums.ClientType;

public class CommonLoginUser {
    private String clientId;
    private String userPkid;
    private String account;
    private String username;
    private String realName;
    private String token;
    private String currentRole;
    private ClientType clientType;

    public CommonLoginUser() {
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

    public String getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
    }

    public void setClientType(final ClientType clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        return "CommonLoginUser(clientId=" + this.getClientId() + ", userPkid=" + this.getUserPkid()
               + ", account=" + this.getAccount() + ", username=" + this.getUsername() + ", realName=" + this
                       .getRealName() + ", token=" + this.getToken() + ", currentRole=" + this.getCurrentRole()+
        ", clientType="
        + this.getClientType() + ")";
    }
}
