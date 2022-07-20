package priv.naj.club.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import priv.naj.club.system.entity.RightsEntity;
import priv.naj.club.system.service.RightsService;

import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 * 路由表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "路由表")
@RestController
@RequestMapping("/system/rightsEntity")
public class RightsController extends CommonAbstractController<RightsEntity,RightsService> {

}
