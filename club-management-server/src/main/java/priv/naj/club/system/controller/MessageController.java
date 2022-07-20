package priv.naj.club.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.common.consts.MessageStatusConstant;
import priv.naj.club.system.entity.MessageEntity;
import priv.naj.club.system.service.MessageService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "消息表")
@RestController
@RequestMapping("/system/messageEntity")
public class MessageController extends CommonAbstractController<MessageEntity, MessageService> {
    @ApiOperation(value = "查询当前用户消息", notes = "查询当前用户消息")
    @GetMapping("searchUserMessages")
    public List<MessageEntity> searchUserMessages(@RequestParam Integer status) {
        return service.searchUserMessages(status);
    }

    @ApiOperation(value = "标记已读", notes = "标记已读")
    @GetMapping("/markRead")
    public void markRead(@RequestParam String messagePkid) {
        service.markRead(messagePkid);
    }
}
