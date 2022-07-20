package priv.naj.club.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import priv.naj.club.system.entity.RolePermissionRelationEntity;
import priv.naj.club.system.service.RolePermissionRelationService;

import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "角色权限关系表")
@RestController
@RequestMapping("/system/rolePermissionRelationEntity")
public class RolePermissionRelationController extends CommonAbstractController<RolePermissionRelationEntity,RolePermissionRelationService> {

}
