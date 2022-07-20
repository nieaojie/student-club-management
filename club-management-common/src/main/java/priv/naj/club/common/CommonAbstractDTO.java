package priv.naj.club.common;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;

public class CommonAbstractDTO {
    @ApiModelProperty("主键ID")
    private String pkid;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("0 未删除, 1 删除")
    private Integer isDeleted;
    @ApiModelProperty("创建人")
    private String creator;
    @ApiModelProperty("创建时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;
    @ApiModelProperty("更新人")
    private String updator;
    @ApiModelProperty("更新时间")
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date updateTime;

    public CommonAbstractDTO() {
    }

    public String getPkid() {
        return this.pkid;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Integer getIsDeleted() {
        return this.isDeleted;
    }

    public String getCreator() {
        return this.creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getUpdator() {
        return this.updator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setPkid(final String pkid) {
        this.pkid = pkid;
    }

    public void setStatus(final Integer status) {
        this.status = status;
    }

    public void setIsDeleted(final Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setCreator(final String creator) {
        this.creator = creator;
    }

    public void setCreateTime(final Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdator(final String updator) {
        this.updator = updator;
    }

    public void setUpdateTime(final Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CommonAbstractDTO{" +
               "pkid='" + pkid + '\'' +
               ", status=" + status +
               ", isDeleted=" + isDeleted +
               ", creator='" + creator + '\'' +
               ", createTime=" + createTime +
               ", updator='" + updator + '\'' +
               ", updateTime=" + updateTime +
               '}';
    }
}
