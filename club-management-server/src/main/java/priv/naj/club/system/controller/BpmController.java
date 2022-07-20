package priv.naj.club.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.system.service.BpmService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * @author nieaojie
 */
@Api(tags = "流程表")
@RestController
@RequestMapping("/system/bpmEntity")
public class BpmController extends CommonAbstractController<BpmEntity, BpmService> {
    @ApiOperation(value = "根据用户id查询用户所有加入社团的审核状态", notes = "根据用户id查询用户所有加入社团的审核状态")
    @GetMapping("/getBpmByUserId")
    public List<BpmEntity> getBpmByUserId(@RequestParam(value = "userPkid") String userPkid) {
        return service.getBpmByUserId(userPkid);
    }
}
