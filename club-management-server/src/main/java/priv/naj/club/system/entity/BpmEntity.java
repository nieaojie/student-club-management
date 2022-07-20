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
 * 流程表
 * </p>
 *
 * @author nieaojie
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_bpm")
@ApiModel(value="BpmEntity对象", description="流程表")
public class BpmEntity extends CommonAbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "提交人")
    private String submitter;

    @ApiModelProperty(value = "提交时间")
    private Date submitTime;

    @ApiModelProperty(value = "审核人")
    private String approver;

    @ApiModelProperty(value = "审核时间")
    private Date processTime;

    @ApiModelProperty(value = "流程表单id")
    private String bpmFormPkid;

    @ApiModelProperty(value = "社团id")
    private String clubPkid;

    @ApiModelProperty(value = "处理人")
    private String processor;
}
