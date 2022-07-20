package priv.naj.club.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;
import priv.naj.club.system.entity.UserClubRoleRelationEntity;
import priv.naj.club.system.service.UserClubRoleRelationService;

import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "用户社团角色关系表")
@RestController
@RequestMapping("/system/userClubRoleRelationEntity")
public class UserClubRoleRelationController extends CommonAbstractController<UserClubRoleRelationEntity,UserClubRoleRelationService> {

}
