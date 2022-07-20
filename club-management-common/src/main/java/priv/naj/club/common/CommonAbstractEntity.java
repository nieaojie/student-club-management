package priv.naj.club.common;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import io.swagger.annotations.ApiModelProperty;

public abstract class CommonAbstractEntity {
    @TableId(
            type = IdType.ASSIGN_ID
    )
    @ApiModelProperty(value = "主键pkid", position = 10)
    private String pkid;
    @ApiModelProperty(value = "状态, 0-未启用,1-启用", position = 2)
    private Integer status;
    @TableLogic
    @ApiModelProperty(value = "0 未删除, 1 删除", position = 3)
    private Integer isDeleted;
    @ApiModelProperty(value = "创建人", position = 5)
    @TableField(fill = FieldFill.INSERT)
    private String creator;
    @ApiModelProperty(value = "创建时间", position = 6)
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人", position = 7)
    private String updator;
    @ApiModelProperty(value = "更新时间", position = 8)
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public CommonAbstractEntity() {
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
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

    protected boolean canEqual(final Object other) {
        return other instanceof CommonAbstractEntity;
    }

    @Override
    public String toString() {
        return "CommonAbstractEntity(pkid=" + this.getPkid() + ", status=" + this.getStatus()
               + ", isDeleted=" + this.getIsDeleted() + ", creator=" + this.getCreator()
               + ", createTime=" + this.getCreateTime()
               + ", updator=" + this.getUpdator() + ", updateTime=" + this.getUpdateTime() + ")";
    }
}
