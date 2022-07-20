package priv.naj.club.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import priv.naj.club.common.CommonAbstractEntity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_message")
@ApiModel(value = "MessageEntity对象", description = "消息表")
public class MessageEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "发送人id")
    private String sender;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    @ApiModelProperty(value = "接受人")
    private String receiver;

    @ApiModelProperty(value = "接收时间")
    private Date receiveTime;

    @ApiModelProperty(value = "消息类型:0-通知,1-系统消息")
    private Integer type;

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "消息标题")
    private String title;

    @ApiModelProperty(value = "流程id")
    private String bpmPkid;
}
