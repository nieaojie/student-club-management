package priv.naj.club.system.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import priv.naj.club.common.CommonAbstractServiceImpl;
import priv.naj.club.common.consts.AuthConstant;
import priv.naj.club.common.consts.BpmConstant;
import priv.naj.club.common.consts.ErrorCodeConst;
import priv.naj.club.common.consts.MessageStatusConstant;
import priv.naj.club.common.consts.MessageTypeConstant;
import priv.naj.club.common.consts.ParamErrorConstant;
import priv.naj.club.common.consts.RoleConstant;
import priv.naj.club.common.context.WebContextHolder;
import priv.naj.club.common.execption.BizException;
import priv.naj.club.common.execption.ParamException;
import priv.naj.club.entity.res.ClubResDto;
import priv.naj.club.system.controller.dto.ClubDetailDTO;
import priv.naj.club.system.controller.dto.NoticeDTO;
import priv.naj.club.system.controller.dto.UserClubRoleRelationDTO;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.controller.dto.UserManageClubDTO;
import priv.naj.club.system.entity.BpmEntity;
import priv.naj.club.system.entity.CategoryEntity;
import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.system.entity.MessageEntity;
import priv.naj.club.system.entity.NoticeEntity;
import priv.naj.club.system.entity.UserClubRoleRelationEntity;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.mapper.ClubMapper;
import priv.naj.club.system.service.BpmService;
import priv.naj.club.system.service.CategoryService;
import priv.naj.club.system.service.ClassService;
import priv.naj.club.system.service.ClubService;
import priv.naj.club.system.service.CollegeService;
import priv.naj.club.system.service.MessageService;
import priv.naj.club.system.service.NoticeService;
import priv.naj.club.system.service.RoleService;
import priv.naj.club.system.service.SpecialtyService;
import priv.naj.club.system.service.UserClubRoleRelationService;
import priv.naj.club.system.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author nieaojie
 */
@Service
@Slf4j
public class ClubServiceImpl extends CommonAbstractServiceImpl<ClubEntity, ClubMapper> implements ClubService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserClubRoleRelationService userClubRoleRelationService;
    @Autowired
    private BpmService bpmService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ClubService clubService;
    @Autowired
    private CollegeService collegeService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ClassService classService;
    @Autowired
    private NoticeService noticeService;

    /**
     * 根据社团id查询社团详细信息
     * @return
     */
    @Override
    public UserManageClubDTO getClubDetailByClubPkid(String clubPkid) {
        if (clubPkid == null) {
            throw new BizException("社团id不能为空");
        }
        UserManageClubDTO userManageClubDTO = new UserManageClubDTO();
        ClubDetailDTO clubDetail = getClubDetail(clubPkid);
        List<UserDTO> userDTOS = listClubMembers(clubPkid);
        userManageClubDTO.setClubDetailDTO(clubDetail);
        userManageClubDTO.setUserDTOS(userDTOS);
        return userManageClubDTO;
    }

    /**
     * 查询社团管理员管理的所有社团
     * @return
     */
    @Override
    public List<ClubEntity> getAllClubByManagePkid() {
        //查询用户管理的社团
        String userPkid = WebContextHolder.getUserPkid();
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).select(UserClubRoleRelationEntity::getClubId).eq(
                UserClubRoleRelationEntity::getUserId, userPkid).in(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.CHAIRMAN, RoleConstant.VICE_CHAIRMAN);
        List<ClubEntity> list = userClubRoleRelationService.list(wrapper).stream().map(
                entity -> clubService.getById(entity.getClubId())).collect(Collectors.toList());
        return list;
    }

    /**
     * 查询普通用户加入的社团
     * @return
     */
    @Override
    public List<ClubResDto> getJoinClubByUserId() {
        String userPkid = WebContextHolder.getUserPkid();
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).select(UserClubRoleRelationEntity::getClubId).eq(
                UserClubRoleRelationEntity::getUserId, userPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.MEMBER).orderByDesc(
                UserClubRoleRelationEntity::getCreateTime);
        List<ClubResDto> list = userClubRoleRelationService.list(wrapper).stream().map(entity -> {
            ClubResDto clubResDto = new ClubResDto();
            ClubEntity clubEntity = clubService.getById(entity.getClubId());
            BeanUtils.copyProperties(clubEntity, clubResDto);
            return clubResDto;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 查询社团管理员管理的社团详细信息
     * @return
     */
    @Override
    public List<UserManageClubDTO> getClubDetailByClubManagePkid() {
        //查询用户管理的社团
        String userPkid = WebContextHolder.getUserPkid();
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).select(UserClubRoleRelationEntity::getClubId).eq(
                UserClubRoleRelationEntity::getUserId, userPkid).in(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.CHAIRMAN, RoleConstant.VICE_CHAIRMAN);
        List<UserManageClubDTO> list = userClubRoleRelationService.list(wrapper).stream().map(entity -> {
            UserManageClubDTO userManageClubDTO = new UserManageClubDTO();
            ClubDetailDTO clubDetail = getClubDetail(entity.getClubId());
            List<UserDTO> userDTOS = listClubMembers(entity.getClubId());
            userManageClubDTO.setClubDetailDTO(clubDetail);
            userManageClubDTO.setUserDTOS(userDTOS);
            return userManageClubDTO;
        }).collect(Collectors.toList());
        return list;
    }

    /**
     * 查询社团的详细信息
     * @param clubPkid
     * @return
     */
    @Override
    public ClubDetailDTO getClubDetail(String clubPkid) {
        //判断社团是否冻结
        ClubEntity clubEntity = getById(clubPkid);
        if (clubEntity.getStatus() == AuthConstant.CLUB_FREEZE) {
            throw new BizException(ErrorCodeConst.NOT_ALLOWED, "该社团已被冻结");
        }
        ClubDetailDTO clubDetailDTO = new ClubDetailDTO();
        //查询社团的基本信息
        BeanUtils.copyProperties(clubEntity, clubDetailDTO);
        //查询社团的分类信息
        CategoryEntity categoryEntity = categoryService.getById(clubEntity.getClubCategoryId());
        clubDetailDTO.setCategoryEntity(categoryEntity);
        //查询社团的会长
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.CHAIRMAN);
        UserClubRoleRelationEntity chairmanRelation = userClubRoleRelationService.getOne(wrapper);
        if (chairmanRelation != null) {
            UserEntity userEntity = userService.getById(chairmanRelation.getUserId());
            clubDetailDTO.setChairman(userEntity);
        }
        //查询社团副会长信息
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper2 = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.VICE_CHAIRMAN);
        List<UserClubRoleRelationEntity> viceChairmanRelationList = userClubRoleRelationService.list(wrapper2);
        if (viceChairmanRelationList.size() > 0) {
            List<UserEntity> userEntityList = viceChairmanRelationList.stream().map(entity -> {
                UserEntity userEntity = userService.getById(entity.getUserId());
                return userEntity;
            }).collect(Collectors.toList());
            clubDetailDTO.setViceChairman(userEntityList);
        }
        //查询社团人数
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper3 = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid);
        int count = userClubRoleRelationService.count(wrapper3);
        clubDetailDTO.setClubPersonCount(count);
        //社团的成立天数
        Date clubCreateTime = clubDetailDTO.getClubCreateTime();
        Date currentDate = new Date();
        long between = DateUtil.between(clubCreateTime, currentDate, DateUnit.DAY);
        clubDetailDTO.setCreateDays((int) between);
        //查询社团公告
        List<NoticeDTO> notice = noticeService.getClubAllNotice(clubPkid, 3);
        clubDetailDTO.setNotices(notice);
        //TODO 其他内容
        return clubDetailDTO;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //删除社团信息
        super.removeById(id);
        //删除用户-社团-角色关系表数据
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, id);
        userClubRoleRelationService.remove(wrapper);
        return true;
    }

    @Override
    @Transactional
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        //删除社团信息
        super.removeByIds(idList);
        //删除用户-社团-角色关系表数据
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).in(UserClubRoleRelationEntity::getClubId, idList);
        userClubRoleRelationService.remove(wrapper);
        return true;
    }

    /**
     * 拒绝加入社团
     * @param messagePkid
     */
    @Override
    public void disagreeJoinClub(String messagePkid) {
        MessageEntity messageEntity = messageService.getById(messagePkid);
        BpmEntity bpmEntity = bpmService.getById(messageEntity.getBpmPkid());
        //修改流程状态
        bpmEntity.setStatus(BpmConstant.NOT_PASS.getValue());
        bpmEntity.setProcessor(WebContextHolder.getUsername());
        bpmEntity.setProcessTime(new Date());
        bpmService.updateById(bpmEntity);
        //修改消息的状态
        messageEntity.setReceiver(WebContextHolder.getUsername());
        messageEntity.setReceiveTime(new Date());
        messageEntity.setStatus(MessageStatusConstant.READ.getValue());
        messageService.updateById(messageEntity);
        //向申请人发送拒绝加入的消息
        String clubName = clubService.getById(bpmEntity.getClubPkid()).getClubName();
        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setSender(WebContextHolder.getUsername());
        messageEntity2.setSendTime(new Date());
        messageEntity2.setReceiver(bpmEntity.getSubmitter());
        messageEntity2.setTitle("拒绝加入");
        messageEntity2.setContent("【" + clubName + "】社团管理员拒绝加入");
        messageEntity2.setType(MessageTypeConstant.NOTIFICATION);
        messageService.createCmd(messageEntity2);
    }

    /**
     * 移除社团用户
     * @param clubPkid
     * @param userPkid
     */
    @Override
    public void removeUserFromClub(String clubPkid, String userPkid) {
        //判断该社团是否存在
        ClubEntity clubEntity = clubService.getById(clubPkid);
        if (clubEntity == null) {
            throw new BizException(ErrorCodeConst.NOT_FOUND, "该社团不存在");
        }
        //移除该社团该用户
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getUserId, userPkid);
        userClubRoleRelationService.remove(wrapper);
        //修改流程中的状态
        LambdaQueryWrapper<BpmEntity> wrapper2 = Wrappers.lambdaQuery(BpmEntity.class).eq(
                BpmEntity::getClubPkid, clubPkid).eq(BpmEntity::getSubmitter, userPkid).ne(BpmEntity::getStatus,
                                                                                           BpmConstant.ALREADY_EXIT
                                                                                                   .getValue());
        BpmEntity bpmEntity = bpmService.getOne(wrapper2);
        if (bpmEntity != null) {
            bpmEntity.setStatus(BpmConstant.ALREADY_EXIT.getValue());
            bpmService.updateById(bpmEntity);
        }
        //TODO 发消息给被移除的用户
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setSender(WebContextHolder.getRealName());
        messageEntity.setSendTime(new Date());
        messageEntity.setReceiver(userPkid);
        messageEntity.setTitle("移除社团");
        messageEntity.setContent("你已被【" + clubEntity.getClubName() + "】社团管理员移除");
        messageEntity.setType(MessageTypeConstant.NOTIFICATION);
        messageService.createCmd(messageEntity);
    }

    /**
     * 查询社团成员
     * @param clubPkid
     * @return
     */
    @Override
    public List<UserDTO> listClubMembers(String clubPkid) {
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid);
        List<UserDTO> userDTOS = userClubRoleRelationService.list(wrapper).stream().map(ucree -> {
            UserDTO userDTO = new UserDTO();
            //用户基本信息
            String userId = ucree.getUserId();
            UserEntity userEntity = userService.getById(userId);
            BeanUtils.copyProperties(userEntity, userDTO);
            //用户在该社团的角色信息
            ArrayList<UserClubRoleRelationDTO> userClubRoleRelationDTOS = new ArrayList<>();
            UserClubRoleRelationDTO userClubRoleRelationDTO = new UserClubRoleRelationDTO();
            userClubRoleRelationDTO.setUserId(userId);
            userClubRoleRelationDTO.setCreateTime(ucree.getCreateTime());
            userClubRoleRelationDTO.setRoleEntity(roleService.getById(ucree.getRoleId()));
            userClubRoleRelationDTO.setClubEntity(clubService.getById(clubPkid));
            userClubRoleRelationDTOS.add(userClubRoleRelationDTO);
            userDTO.setUserClubRoleRelationDTOs(userClubRoleRelationDTOS);
            //院系专业班级信息
            userDTO.setUserCollege(collegeService.getById(userEntity.getUserCollege()));
            userDTO.setUserSpecialty(specialtyService.getById(userEntity.getUserSpecialty()));
            userDTO.setUserClass(classService.getById(userEntity.getUserClass()));
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    /**
     * 设置副会长
     * @param clubPkid
     * @param userPkids
     */
    @Override
    public void setClubViceChairman(String clubPkid, List<String> userPkids) {
        //修改该社团原来的副会长角色为社团成员
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.VICE_CHAIRMAN);
        userClubRoleRelationService.list(wrapper).stream().forEach(entity -> {
            entity.setRoleId(RoleConstant.MEMBER);
            userClubRoleRelationService.updateById(entity);
        });
        //设置该社团新的副会长【添加不存在的，修改已存在的】
        userPkids.stream().forEach(userPkid -> {
            LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper2 = Wrappers.lambdaQuery(
                    UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                    UserClubRoleRelationEntity::getUserId, userPkid);
            UserClubRoleRelationEntity userClubRoleRelationEntity = userClubRoleRelationService.getOne(
                    wrapper2);
            if (userClubRoleRelationEntity != null) {
                userClubRoleRelationEntity.setRoleId(RoleConstant.VICE_CHAIRMAN);
                userClubRoleRelationService.updateById(userClubRoleRelationEntity);
            } else {
                userClubRoleRelationEntity = new UserClubRoleRelationEntity();
                userClubRoleRelationEntity.setRoleId(RoleConstant.VICE_CHAIRMAN);
                userClubRoleRelationEntity.setClubId(clubPkid);
                userClubRoleRelationEntity.setUserId(userPkid);
                userClubRoleRelationService.createCmd(userClubRoleRelationEntity);
            }
        });
    }

    /**
     * 设置会长
     * @param clubPkid
     * @param userPkid
     */
    @Override
    public void setClubChairman(String clubPkid, String userPkid) {
        //移除原来社团会长，将其角色变为社团会员
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getRoleId,
                                                     RoleConstant.CHAIRMAN).eq(
                UserClubRoleRelationEntity::getClubId, clubPkid);
        UserClubRoleRelationEntity userClubRoleRelationEntity = userClubRoleRelationService.getOne(wrapper);
        if (userClubRoleRelationEntity != null) {
            userClubRoleRelationEntity.setRoleId(RoleConstant.MEMBER);
            userClubRoleRelationService.updateById(userClubRoleRelationEntity);
        }
        //修改原来社团该用户的角色，不存在则添加新的社团会长记录【用户-社团-角色】
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper2 = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getUserId,
                                                     userPkid).eq(
                UserClubRoleRelationEntity::getClubId, clubPkid);
        UserClubRoleRelationEntity userClubRoleRelationEntity2 = userClubRoleRelationService.getOne(wrapper2);
        if (userClubRoleRelationEntity2 != null) {
            userClubRoleRelationEntity2.setRoleId(RoleConstant.CHAIRMAN);
            userClubRoleRelationService.updateById(userClubRoleRelationEntity2);
        } else {
            userClubRoleRelationEntity2 = new UserClubRoleRelationEntity();
            userClubRoleRelationEntity2.setUserId(userPkid);
            userClubRoleRelationEntity2.setRoleId(RoleConstant.CHAIRMAN);
            userClubRoleRelationEntity2.setClubId(clubPkid);
            userClubRoleRelationService.createCmd(userClubRoleRelationEntity2);
        }
    }

    /**
     * 查询社团成员
     * @param clubPkid
     * @return
     */
    @Override
    public List<UserDTO> searchClubUsers(String clubPkid) {
        //注意使用distinct方法过滤重复的数据时，需要重写equals方法，否则两个属性值相同的对象也不能过滤
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).select(UserClubRoleRelationEntity::getUserId).eq(
                UserClubRoleRelationEntity::getClubId, clubPkid);
        List<String> userPkids = userClubRoleRelationService.list(wrapper).stream().map(
                userClubRoleRelationEntity -> userClubRoleRelationEntity.getUserId()).distinct().collect(
                Collectors.toList());

        List<UserDTO> userDTOS = userPkids.stream().map(userPkid -> {
            UserDTO userDTO = new UserDTO();
            UserEntity userEntity = userService.getById(userPkid);
            BeanUtils.copyProperties(userEntity, userDTO);
            userDTO.setUserCollege(collegeService.getById(userEntity.getUserCollege()));
            userDTO.setUserSpecialty(specialtyService.getById(userEntity.getUserSpecialty()));
            userDTO.setUserClass(classService.getById(userEntity.getUserClass()));
            return userDTO;
        }).collect(Collectors.toList());
        return userDTOS;
    }

    /**
     * 同意加入社团
     * @param messagePkid
     */
    @Override
    @Transactional
    public void agreeJoinClub(String messagePkid) {
        //查询消息实体【获取流程id】
        MessageEntity messageEntity = messageService.getById(messagePkid);
        //获得流程
        BpmEntity bpmEntity = bpmService.getById(messageEntity.getBpmPkid());
        if (bpmEntity == null) {
            throw new BizException(ErrorCodeConst.NOT_FOUND, "该申请不存在");
        }
        //修改流程状态
        bpmEntity.setProcessor(WebContextHolder.getUsername());
        bpmEntity.setProcessTime(new Date());
        bpmEntity.setStatus(BpmConstant.ALREADY_JOIN.getValue());
        bpmService.updateById(bpmEntity);
        //用户-社团-角色关系表中添加一条数据
        UserClubRoleRelationEntity userClubRoleRelationEntity = new UserClubRoleRelationEntity();
        userClubRoleRelationEntity.setUserId(bpmEntity.getSubmitter());
        userClubRoleRelationEntity.setClubId(bpmEntity.getClubPkid());
        userClubRoleRelationEntity.setRoleId(RoleConstant.MEMBER);
        userClubRoleRelationService.save(userClubRoleRelationEntity);
        //修改消息的状态为已读
        messageEntity.setStatus(MessageStatusConstant.READ.getValue());
        messageEntity.setReceiveTime(new Date());
        messageService.updateById(messageEntity);
        //发送消息给申请人
        String clubName = clubService.getById(bpmEntity.getClubPkid()).getClubName();
        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setSender(WebContextHolder.getUsername());
        log.info("userRealName:" + WebContextHolder.getUsername());
        messageEntity2.setSendTime(new Date());
        messageEntity2.setReceiver(bpmEntity.getSubmitter());
        messageEntity2.setTitle("申请通过");
        messageEntity2.setContent("你已成功加入社团【" + clubName + "】");
        messageEntity2.setType(MessageTypeConstant.NOTIFICATION);
        messageService.createCmd(messageEntity2);
    }

    /**
     * 退出社团
     * @param clubPkid
     */
    @Override
    public void exitClub(String clubPkid) {
        //判断社团是否存在
        Integer count = lambdaQuery().eq(ClubEntity::getPkid, clubPkid).count();
        if (count == 0) {
            throw new BizException(ErrorCodeConst.NOT_FOUND, "社团不存在");
        }
        //删除user-club-role-relation中的记录
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getUserId, WebContextHolder.getUserPkid());
        userClubRoleRelationService.remove(wrapper);
        //修改流程表中的记录状态
        LambdaQueryWrapper<BpmEntity> wrapper2 = Wrappers.lambdaQuery(BpmEntity.class).eq(
                BpmEntity::getClubPkid, clubPkid).eq(BpmEntity::getSubmitter, WebContextHolder.getUserPkid())
                                                         .ne(BpmEntity::getStatus,
                                                             BpmConstant.ALREADY_EXIT.getValue());
        BpmEntity bpmEntity = bpmService.getOne(wrapper2);
        if (bpmEntity != null) {
            bpmEntity.setStatus(BpmConstant.ALREADY_EXIT.getValue());
            bpmService.updateById(bpmEntity);
            //系统向社团管理员发送消息
            String clubName = clubService.getById(bpmEntity.getClubPkid()).getClubName();
            MessageEntity messageEntity2 = new MessageEntity();
            messageEntity2.setSender(WebContextHolder.getUsername());
            messageEntity2.setSendTime(new Date());
            messageEntity2.setReceiver(bpmEntity.getApprover());
            messageEntity2.setTitle("退出社团");
            messageEntity2.setContent(WebContextHolder.getUsername() + "已退出社团【" + clubName + "】");
            messageEntity2.setType(MessageTypeConstant.NOTIFICATION);
            messageEntity2.setBpmPkid(bpmEntity.getPkid());
            messageService.createCmd(messageEntity2);
        }
    }

    /**
     * 判断用户与当前社团的关系
     * @return
     * @param clubPkid
     */
    @Override
    public BpmConstant judgeClubAndUserRelation(String clubPkid) {
        //判断当前社团是否是用户已经加入的社团
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getUserId,
                                                     WebContextHolder.getUserPkid()).eq(
                UserClubRoleRelationEntity::getClubId, clubPkid);
        UserClubRoleRelationEntity userClubRoleRelationEntity = userClubRoleRelationService.getOne(wrapper);
        if (userClubRoleRelationEntity != null) {
            return BpmConstant.ALREADY_JOIN;
        }
        //查询用户是否提交了加入该社团申请，从sys_bpm表中查询
        LambdaQueryWrapper<BpmEntity> wrapper2 = Wrappers.lambdaQuery(BpmEntity.class).eq(
                BpmEntity::getSubmitter, WebContextHolder.getUserPkid()).eq(
                BpmEntity::getClubPkid, clubPkid).ne(BpmEntity::getStatus, BpmConstant.ALREADY_EXIT.getValue());
        BpmEntity bpmEntity = bpmService.getOne(wrapper2);
        if (bpmEntity != null) {
            //判断审核的状态，0-待审核，1-已通过（已加入），2-未通过
            if (bpmEntity.getStatus() == 0) {
                return BpmConstant.WAITING_PASS;
            }
            if (bpmEntity.getStatus() == 1) {
                return BpmConstant.ALREADY_JOIN;
            }
            if (bpmEntity.getStatus() == 2) {
                return BpmConstant.NOT_PASS;
            }
            if (bpmEntity.getStatus() == 3) {
                return BpmConstant.NOT_JOIN;
            }
            if (bpmEntity.getStatus() == 4) {
                return BpmConstant.ALREADY_EXIT;
            }
        }
        return BpmConstant.NOT_JOIN;
    }

    @Override
    public IPage<ClubResDto> listClubWithClubCategory(Integer pageNo, Integer pageSize) {
        IPage<ClubEntity> page = super.pageQry(pageNo, pageSize);
        List<ClubEntity> entities = page.getRecords();
        List<ClubResDto> clubResDtos = entities.stream().map(entity -> {
            ClubResDto clubResDto = new ClubResDto();
            CategoryEntity categoryEntity = categoryService.getById(entity.getClubCategoryId());
            clubResDto.setClubCategoryEntity(categoryEntity);
            BeanUtils.copyProperties(entity, clubResDto);
            //查询会长
            String clubPkid = entity.getPkid();
            LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                    UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                    UserClubRoleRelationEntity::getRoleId, RoleConstant.CHAIRMAN);
            UserClubRoleRelationEntity one = userClubRoleRelationService.getOne(wrapper);
            if (one != null) {
                //查询该用户实体
                UserEntity userEntity = userService.getById(one.getUserId());
                //设置会长
                clubResDto.setChairman(userEntity);
                log.info("会长：" + userEntity);
            }
            //查询副会长
            LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper2 = Wrappers.lambdaQuery(
                    UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                    UserClubRoleRelationEntity::getRoleId, RoleConstant.VICE_CHAIRMAN);
            List<UserEntity> userEntities = userClubRoleRelationService.list(wrapper2).stream().map(
                    ucrrs -> userService.getById(ucrrs.getUserId())).collect(Collectors.toList());
            clubResDto.setViceChairman(userEntities);
            log.info("副会长：" + userEntities);
            return clubResDto;
        }).collect(Collectors.toList());
        IPage<ClubResDto> iPage = new Page<>();
        iPage.setRecords(clubResDtos);
        iPage.setTotal(page.getTotal());
        iPage.setCurrent(page.getCurrent());
        iPage.setPages(page.getPages());
        return iPage;
    }

    /**
     * 模糊查询
     * @param searchParam
     * @param page
     * @return
     */
    @Override
    public IPage<ClubResDto> searchByLike(String searchParam, Page<ClubEntity> page) {
        LambdaQueryWrapper<ClubEntity> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<CategoryEntity> lambdaQueryWrapper = Wrappers.lambdaQuery(CategoryEntity.class)
                                                                        .select(CategoryEntity::getPkid,
                                                                                CategoryEntity::getCategoryName);
        List<String> list = categoryService.list(lambdaQueryWrapper).stream().map(categoryEntity -> {
            if (searchParam.equals(categoryEntity.getCategoryName())) {
                return categoryEntity.getPkid();
            }
            return null;
        }).collect(Collectors.toList());
        if (list.size() > 0) {
            wrapper.in(ClubEntity::getClubCategoryId, list).or();
        }
        wrapper.likeRight(ClubEntity::getClubName, searchParam).or()
               .likeRight(ClubEntity::getClubCode, searchParam).or()
               .likeRight(ClubEntity::getPkid, searchParam).or()
               .likeRight(ClubEntity::getCreator, searchParam).or()
               .likeRight(ClubEntity::getClubPersonCount, searchParam).or()
               .likeRight(ClubEntity::getUpdator, searchParam)
               .orderByDesc(ClubEntity::getUpdateTime);
        Page<ClubEntity> page1 = super.page(page, wrapper);
        List<ClubResDto> clubResDtos = page1.getRecords().stream().map(entity -> {
            ClubResDto clubResDto = new ClubResDto();
            CategoryEntity categoryEntity = categoryService.getById(entity.getClubCategoryId());
            clubResDto.setClubCategoryEntity(categoryEntity);
            String clubPkid = entity.getPkid();
            //设置会长、副会长
            clubResDto.setChairman(userService.getById(getChairmanPkid(clubPkid)));
            List<UserEntity> viceChairman = getViceChairmanPkids(clubPkid).stream().map(
                    userPkid -> userService.getById(userPkid)).collect(Collectors.toList());
            clubResDto.setViceChairman(viceChairman);
            BeanUtils.copyProperties(entity, clubResDto);
            return clubResDto;
        }).collect(Collectors.toList());
        IPage<ClubResDto> iPage = new Page<>();
        BeanUtils.copyProperties(page1, iPage);
        iPage.setRecords(clubResDtos);
        return iPage;
    }

    @Override
    public String createCmd(ClubEntity entity) {
        if (entity == null) {
            throw new BizException(ParamErrorConstant.PARAM_IS_NULL, "参数不能为空");
        }
        boolean b = checkClubIsExist(entity);
        if (b) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "社团名称或社团编码已被使用");
        }
        String uuid = uuidGenerator.newUUID();
        entity.setCreateTime(new Date());
        entity.setPkid(uuid);
        entity.setCreator(WebContextHolder.getUsername());
        save(entity);
        return uuid;
    }

    @Override
    public ClubEntity infoQry(String pkid) {
        if (pkid == null) {
            throw new ParamException("500", "id不能为空");
        }
        ClubEntity entity = getById(pkid);
        if (entity != null) {
            String categoryId = entity.getClubCategoryId();
            entity.setCategoryName(categoryService.getById(categoryId).getCategoryName());
            return entity;
        }
        return null;
    }

    @Override
    public boolean updateByIdCmd(ClubEntity entity) {
        boolean isExist = checkClubIsExist(entity);
        if (isExist) {
            throw new BizException(ErrorCodeConst.DUPLICATE, "角色名称或角色编码已存在");
        } else {
            return super.updateByIdCmd(entity);
        }
    }

    /**
     * 根据roleCode和roleName检查角色是否存在
     * @param clubEntity
     * @return true-存在，false-不存在
     */
    public boolean checkClubIsExist(ClubEntity clubEntity) {
        Integer count = lambdaQuery().ne(ClubEntity::getPkid, clubEntity.getPkid()).and(wrapper -> wrapper.eq(
                ClubEntity::getClubName, clubEntity.getClubName()).or().eq(ClubEntity::getClubCode,
                                                                           clubEntity.getClubCode())).count();
        return count > 0 ? true : false;
    }

    /**
     * 根据社团id查询会长
     * @param clubPkid
     * @return userPkid
     */
    @Override
    public String getChairmanPkid(String clubPkid) {
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.CHAIRMAN);
        UserClubRoleRelationEntity chairman = userClubRoleRelationService.getOne(wrapper);
        if (chairman != null) {
            return chairman.getUserId();
        }
        return null;
    }

    /**
     * 根据社团id查询副会长
     * @param clubPkid
     * @return userPkids
     */
    @Override
    public List<String> getViceChairmanPkids(String clubPkid) {
        LambdaQueryWrapper<UserClubRoleRelationEntity> wrapper = Wrappers.lambdaQuery(
                UserClubRoleRelationEntity.class).eq(UserClubRoleRelationEntity::getClubId, clubPkid).eq(
                UserClubRoleRelationEntity::getRoleId, RoleConstant.VICE_CHAIRMAN);
        List<String> viceChairmans = userClubRoleRelationService.list(wrapper).stream().map(
                entity -> entity.getUserId()).collect(Collectors.toList());
        return viceChairmans;
    }
}
