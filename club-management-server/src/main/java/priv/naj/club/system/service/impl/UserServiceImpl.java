package priv.naj.club.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.common.config.redis.RedisUtil;
import priv.naj.club.common.consts.AuthCacheConstant;
import priv.naj.club.common.consts.AuthErrorCodeConstant;
import priv.naj.club.common.consts.BpmConstant;
import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.MessageTypeConstant;
import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.consts.RoleConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.common.util.PasswordUtil;
import priv.naj.club.entity.model.CommonLoginUser;
import priv.naj.club.entity.res.LoginRes;
import priv.naj.club.security.SecurityProperties;
import priv.naj.club.system.controller.dto.UserClubRoleRelationDTO;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.system.entity.ClassEntity;
import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.system.entity.CollegeEntity;
import priv.naj.club.system.entity.MessageEntity;
import priv.naj.club.system.entity.RoleEntity;
import priv.naj.club.system.entity.SpecialtyEntity;
import priv.naj.club.system.entity.UserClubRoleRelationEntity;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.mapper.UserMapper;
import priv.naj.club.system.service.BpmService;
import priv.naj.club.system.service.ClassService;
import priv.naj.club.system.service.ClubService;
import priv.naj.club.system.service.CollegeService;
import priv.naj.club.system.service.MessageService;
import priv.naj.club.system.service.RoleService;
import priv.naj.club.system.service.SpecialtyService;
import priv.naj.club.system.service.UserClubRoleRelationService;
import priv.naj.club.system.service.UserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
@Slf4j
public class UserServiceImpl extends CommonAbstractServiceImpl<UserEntity, UserMapper> implements UserService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private UserClubRoleRelationService userClubRoleRelationService;
    @Autowired
    private BpmService bpmService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ClassService classService;

    /**
     * 申请加入社团
     * @param
     * @return
     */
    @Transactional
    @Override
    public Boolean joinClub(String clubPkid) {
        //String userPkid = WebContextHolder.getUserPkid();
        //log.info("userPkid:" + userPkid);
        //判断是否加入该社团
        LambdaQueryWrapper<UserClubRoleRelationEntity> w = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getUserId,
                                                     WebContextHolder.getUserPkid()).eq(
                UserClubRoleRelationEntity::getClubId, clubPkid).eq(UserClubRoleRelationEntity::getStatus, 1);
        int count = userClubRoleRelationService.count(w);
        if (count > 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "已经加入该社团");
        }
        //判断是否在审核中
        LambdaQueryWrapper<BpmEntity> w2 = Wrappers.lambdaQuery(BpmEntity.class).eq(BpmEntity::getSubmitter,
                                                                                    WebContextHolder
                                                                                            .getUserPkid()).eq(
                BpmEntity::getClubPkid, clubPkid).eq(BpmEntity::getStatus, BpmConstant.WAITING_PASS.getValue());
        int c2 = bpmService.count(w2);
        if (c2 > 0) {
            throw new BizException(ErrorCodeConst.DURING_OPERATION, "正在审核中");
        }
        BpmEntity entity = new BpmEntity();
        entity.setSubmitter(WebContextHolder.getUserPkid());
        entity.setSubmitTime(new Date());
        //查询社团负责人【会长、副会长】
        List<String> userPkids = new ArrayList<>();
        List<String> viceChairmanPkids = clubService.getViceChairmanPkids(clubPkid);
        String chairmanPkid = clubService.getChairmanPkid(clubPkid);
        if (viceChairmanPkids.size() > 0) {
            BeanUtils.copyProperties(viceChairmanPkids, userPkids);
        }
        if (StringUtils.isNotBlank(chairmanPkid)) {
            userPkids.add(chairmanPkid);
        }
        String approvers = StringUtils.join(userPkids, ",");
        log.info("join:" + approvers);
        if (approvers.startsWith(",")) {
            approvers = approvers.substring(1);
        }
        entity.setApprover(approvers);
        entity.setClubPkid(clubPkid);
        //判断是否拒绝通过，是-重新修改流程状态为待审核
        LambdaQueryWrapper<BpmEntity> wrapper2 = Wrappers.lambdaQuery(BpmEntity.class).eq(
                BpmEntity::getSubmitter, WebContextHolder.getUserPkid()).eq(
                BpmEntity::getClubPkid, clubPkid).eq(BpmEntity::getStatus, BpmConstant.NOT_PASS.getValue());
        BpmEntity bpmEntity = bpmService.getOne(wrapper2);
        String bpmPkid = "";
        if (bpmEntity != null) {
            bpmPkid = bpmEntity.getPkid();
            bpmEntity.setStatus(BpmConstant.WAITING_PASS.getValue());
            bpmService.updateById(bpmEntity);
        } else {
            //在审核表中添加该条审核记录
            bpmPkid = bpmService.createCmd(entity);
        }
        String userRealname = getById(entity.getSubmitter()).getUserRealname();
        //向社团负责人发送消息
        String clubName = clubService.getById(clubPkid).getClubName();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(WebContextHolder.getUsername());
        messageEntity.setSendTime(new Date());
        messageEntity.setType(MessageTypeConstant.SYSTEM_MESSAGE);
        messageEntity.setTitle("社团申请");
        messageEntity.setContent("【" + userRealname + "】请求加入社团【" + clubName + "】");
        messageEntity.setBpmPkid(bpmPkid);
        messageEntity.setReceiver(approvers);
        messageService.createCmd(messageEntity);
        return true;
    }

    /**
     * 根据pkid查询用户详细信息
     * @param pkid
     * @return
     */
    @Override
    public UserDTO infoCmd(String pkid) {
        UserEntity entity = getById(pkid);
        if (entity == null) {
            throw new BizException(ErrorCodeConst.NOT_FOUND, "该用户不存在");
        }
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getUserId,
                                                     entity.getPkid());
        List<UserClubRoleRelationEntity> ucrres = userClubRoleRelationService.list(wrapper);
        List<UserClubRoleRelationDTO> userClubRoleRelationDTOS = ucrres.stream().map(ucree -> {
            RoleEntity roleEntity = roleService.getById(ucree.getRoleId());
            ClubEntity clubEntity = clubService.getById(ucree.getClubId());
            UserClubRoleRelationDTO clubRoleRelationDTO = new UserClubRoleRelationDTO();
            clubRoleRelationDTO.setClubEntity(clubEntity);
            clubRoleRelationDTO.setRoleEntity(roleEntity);
            clubRoleRelationDTO.setUserId(ucree.getUserId());
            clubRoleRelationDTO.setCreateTime(ucree.getCreateTime());
            return clubRoleRelationDTO;
        }).collect(Collectors.toList());
        dto.setUserClubRoleRelationDTOs(userClubRoleRelationDTOS);
        dto.setBpmEntity(bpmService.getBpmByUserId(pkid));
        //查询CollegeEntity、SpecialtyEntity和ClassEntity
        String collegePkid = entity.getUserCollege();
        String specialtyPkid = entity.getUserSpecialty();
        String classPkid = entity.getUserClass();
        CollegeEntity collegeEntity = collegeService.getById(collegePkid);
        SpecialtyEntity specialtyEntity = specialtyService.getById(specialtyPkid);
        ClassEntity classEntity = classService.getById(classPkid);
        dto.setUserCollege(collegeEntity);
        dto.setUserSpecialty(specialtyEntity);
        dto.setUserClass(classEntity);
        //查询用户的角色
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper1 = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).select(UserClubRoleRelationEntity::getRoleId).eq(
                UserClubRoleRelationEntity::getUserId, pkid);
        List<String> rolePkids = userClubRoleRelationService.list(wrapper1).stream().map(e -> e.getRoleId())
                                                            .distinct().collect(Collectors.toList());
        if (rolePkids.contains(RoleConstant.CHAIRMAN) || rolePkids.contains(RoleConstant.VICE_CHAIRMAN)) {
            dto.setCurrentRole("1");
        } else if ("admin".equals(entity.getUserUsername())) {
            dto.setCurrentRole("0");
        } else {
            dto.setCurrentRole("2");
        }
        return dto;
    }

    /**
     * 查看所有用户详细信息
     * @param page
     * @return
     */
    @Override
    public IPage<UserDTO> listUserWithOtherInfo(Page<UserEntity> page) {
        Page<UserEntity> page1 = page(page);
        List<UserEntity> entityList = page1.getRecords();
        List<UserDTO> userDTOS = entityList.stream().map(userEntity -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(userEntity, userDTO);
            LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                    UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getUserId,
                                                         userEntity.getPkid());
            List<UserClubRoleRelationEntity> ucrres = userClubRoleRelationService.list(wrapper);
            List<UserClubRoleRelationDTO> userClubRoleRelationDTOS = ucrres.stream().map(ucree -> {
                RoleEntity roleEntity = roleService.getById(ucree.getRoleId());
                ClubEntity clubEntity = clubService.getById(ucree.getClubId());
                UserClubRoleRelationDTO clubRoleRelationDTO = new UserClubRoleRelationDTO();
                clubRoleRelationDTO.setClubEntity(clubEntity);
                clubRoleRelationDTO.setRoleEntity(roleEntity);
                clubRoleRelationDTO.setUserId(ucree.getUserId());
                clubRoleRelationDTO.setCreateTime(ucree.getCreateTime());
                return clubRoleRelationDTO;
            }).collect(Collectors.toList());
            userDTO.setUserClubRoleRelationDTOs(userClubRoleRelationDTOS);
            userDTO.setBpmEntity(bpmService.getBpmByUserId(userEntity.getPkid()));
            //查询CollegeEntity、SpecialtyEntity和ClassEntity
            String collegePkid = userEntity.getUserCollege();
            String specialtyPkid = userEntity.getUserSpecialty();
            String classPkid = userEntity.getUserClass();
            CollegeEntity collegeEntity = collegeService.getById(collegePkid);
            SpecialtyEntity specialtyEntity = specialtyService.getById(specialtyPkid);
            ClassEntity classEntity = classService.getById(classPkid);
            userDTO.setUserCollege(collegeEntity);
            userDTO.setUserSpecialty(specialtyEntity);
            userDTO.setUserClass(classEntity);
            return userDTO;
        }).collect(Collectors.toList());
        Page<UserDTO> page2 = new Page<>();
        BeanUtils.copyProperties(page1, page2);
        page2.setRecords(userDTOS);
        return page2;
    }

    /**
     * 获取登录信息
     * @param userEntity
     * @return
     */
    @Override
    public LoginRes getLoginInfo(UserEntity userEntity) {
        //生成token
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = AuthCacheConstant.PREFIX_USER_TOKEN + token;
        redisUtil.set(key, userEntity.getUserUsername());
        redisUtil.expire(key, 3600 * 2);
        LoginRes res = new LoginRes();
        UserDTO userDTO = infoCmd(userEntity.getPkid());
        res.setUserDTO(userDTO);
        res.setToken(token);
//        WebContext webContext = new WebContext();
//        webContext.setUsername(userEntity.getUserUsername());
//        webContext.setUserPkid(userEntity.getPkid());
//        webContext.setToken(token);
//        WebContextHolder.set(webContext);
        return res;
    }

    /**
     * 通过用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public UserEntity getByUserName(String username) {
        UserEntity entity = lambdaQuery().eq(UserEntity::getUserUsername, username).one();
        if (entity != null) {
            return entity;
        }
        return null;
    }

    /**
     * 检查用户是否有效
     * @param user
     */
    @Override
    public void checkUserIsEffective(UserEntity user) {
        if (user == null) {
            throw new BizException(AuthErrorCodeConstant.USER_NOT_EXIST, "用户不存在");
        }
        if (user.getIsDeleted() == 1) {
            throw new BizException(AuthErrorCodeConstant.USER_LOGOUT, "用户已注销");
        }
        if (user.getStatus() == 0) {
            throw new BizException(AuthErrorCodeConstant.USER_FREEZE, "用户账号已冻结");
        }
    }

    /**
     * 检验用户名是否可用
     * @param userUsername
     */
    @Override
    public void userNameCanUse(String userUsername) {
        UserEntity userEntity = getByUserName(userUsername);
        if (userEntity != null) {
            throw new BizException(AuthErrorCodeConstant.USERNAME_ALREADY_EXIST, "用户名已被使用");
        }
    }

    @Override
    public CommonLoginUser getUserByToken(String token) {
        final String TEST_PREFIX = "test-";
        String username = null;

        if (securityProperties.isEnableTestToken() && StringUtils.startsWith(token, TEST_PREFIX)) {
            username = token.replace(TEST_PREFIX, "");
        }

        if (StringUtils.isEmpty(username)) {
            username = (String) redisUtil.get(AuthCacheConstant.PREFIX_USER_TOKEN + token);
        }

        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserEntity userEntity = getByUserName(username);
        CommonLoginUser loginUser = new CommonLoginUser();
        loginUser.setToken(token);
        loginUser.setAccount(userEntity.getUserUsername());
        loginUser.setUsername(username);
        loginUser.setRealName(userEntity.getUserRealname());
        loginUser.setUserPkid(userEntity.getPkid());
        loginUser.setClientId("clientId");
        return loginUser;
    }

    @Override
    public String createCmd(UserEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        String username = entity.getUserUsername();
        Integer count = lambdaQuery().eq(UserEntity::getUserUsername, username).count();
        if (count > 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "该用户名已被使用");
        }
        Integer count1 = lambdaQuery().eq(UserEntity::getUserPhone, entity.getUserPhone()).count();
        if (count1 > 0) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "该手机号已被使用");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setPkid(uuid);
        String salt = PasswordUtil.randomGen(8);
        String encrypt = PasswordUtil.encrypt(entity.getUserUsername(), entity.getUserPassword(), salt);
        entity.setUserSalt(salt);
        entity.setUserPassword(encrypt);
        save(entity);
        return uuid;
    }
}
