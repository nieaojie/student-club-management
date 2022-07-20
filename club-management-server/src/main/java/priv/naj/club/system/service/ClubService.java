package priv.naj.club.system.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import priv.naj.club.common.CommonAbstractService;
import priv.naj.club.common.consts.BpmConstant;
import priv.naj.club.entity.res.ClubResDto;
import priv.naj.club.system.controller.dto.ClubDetailDTO;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.controller.dto.UserManageClubDTO;
import priv.naj.club.system.entity.ClubEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nieaojie
 */
public interface ClubService extends CommonAbstractService<ClubEntity> {
    IPage<ClubResDto> listClubWithClubCategory(Integer pageNo, Integer pageSize);

    IPage<ClubResDto> searchByLike(String searchParam, Page<ClubEntity> page);

    BpmConstant judgeClubAndUserRelation(String clubPkid);

    void exitClub(String clubPkid);

    void agreeJoinClub(String messagePkid);

    List<UserDTO> searchClubUsers(String clubPkid);

    void setClubChairman(String clubPkid, String userPkid);

    void setClubViceChairman(String clubPkid, List<String> userPkids);

    List<UserDTO> listClubMembers(String clubPkid);

    void removeUserFromClub(String clubPkid, String userPkid);

    void disagreeJoinClub(String messagePkid);

    ClubDetailDTO getClubDetail(String clubPkid);
    /**
     * 根据社团id查询会长
     * @param clubPkid
     * @return userPkid
     */
    String getChairmanPkid(String clubPkid);

    /**
     * 根据社团id查询副会长
     * @param clubPkid
     * @return userPkids
     */
    List<String> getViceChairmanPkids(String clubPkid);

    List<UserManageClubDTO> getClubDetailByClubManagePkid();

    List<ClubResDto> getJoinClubByUserId();

    List<ClubEntity> getAllClubByManagePkid();

    UserManageClubDTO getClubDetailByClubPkid(String clubPkid);
}
