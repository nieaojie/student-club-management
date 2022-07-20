package priv.naj.club.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import priv.naj.club.common.CommonAbstractController;
import priv.naj.club.common.consts.BpmConstant;
import priv.naj.club.common.response.CommonResult;
import priv.naj.club.common.response.NoCommonResponse;
import priv.naj.club.entity.res.ClubResDto;
import priv.naj.club.system.controller.dto.ClubDetailDTO;
import priv.naj.club.system.controller.dto.UserDTO;
import priv.naj.club.system.controller.dto.UserManageClubDTO;
import priv.naj.club.system.entity.ClubEntity;
import priv.naj.club.system.entity.UserEntity;
import priv.naj.club.system.service.ClubService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author nieaojie
 */
@Api(tags = "社团表")
@RestController
@RequestMapping("/system/clubEntity")
public class ClubController extends CommonAbstractController<ClubEntity, ClubService> {
    @Autowired
    private ClubService clubService;

    @ApiOperation(value = "根据社团id查询社团详细信息", notes = "根据社团id查询社团详细信息")
    @GetMapping("/getClubDetailByClubPkid")
    public UserManageClubDTO getClubDetailByClubPkid(@RequestParam(value = "clubPkid") String clubPkid) {
        return clubService.getClubDetailByClubPkid(clubPkid);
    }

    @ApiOperation(value = "查询社团管理员管理的所有社团", notes = "查询社团管理员管理的所有社团")
    @GetMapping("/getAllClubByManagePkid")
    public List<ClubEntity> getAllClubByManagePkid() {
        return clubService.getAllClubByManagePkid();
    }

    @ApiOperation(value = "查询普通用户加入的社团", notes = "查询普通用户加入的社团")
    @GetMapping("/getJoinClubByUserId")
    public List<ClubResDto> getJoinClubByUserId() {
        return clubService.getJoinClubByUserId();
    }

    @ApiOperation(value = "查询社团管理员管理的社团详细信息", notes = "查询社团管理员管理的社团详细信息")
    @GetMapping("/getClubDetailByClubManagePkid")
    public List<UserManageClubDTO> getClubDetailByClubManagePkid() {
        return clubService.getClubDetailByClubManagePkid();
    }

    @ApiOperation(value = "查询社团的详细信息", notes = "查询社团的详细信息")
    @GetMapping("/getClubDetail")
    public ClubDetailDTO getClubDetail(@RequestParam String clubPkid) {
        return clubService.getClubDetail(clubPkid);
    }

    @ApiOperation(value = "拒绝加入社团", notes = "拒绝加入社团")
    @GetMapping("/disagreeJoinClub")
    public void disagreeJoinClub(@RequestParam String messagePkid) {
        clubService.disagreeJoinClub(messagePkid);
    }

    @ApiOperation(value = "移除社团用户", notes = "移除社团用户")
    @GetMapping("/removeUserFromClub")
    public void removeUserFromClub(@RequestParam String clubPkid, @RequestParam String userPkid) {
        clubService.removeUserFromClub(clubPkid, userPkid);
    }

    @ApiOperation(value = "查看社团成员", notes = "查看社团成员")
    @GetMapping("/listClubMembers")
    public List<UserDTO> listClubMembers(@RequestParam String clubPkid) {
        return clubService.listClubMembers(clubPkid);
    }

    @ApiOperation(value = "设置副会长", notes = "设置副会长")
    @GetMapping("/setClubViceChairman")
    public void setClubViceChairman(@RequestParam String clubPkid, @RequestParam List<String> userPkids) {
        clubService.setClubViceChairman(clubPkid, userPkids);
    }

    @ApiOperation(value = "设置会长", notes = "设置会长")
    @GetMapping("/setClubChairman")
    public void setClubChairman(@RequestParam String clubPkid, @RequestParam String userPkid) {
        clubService.setClubChairman(clubPkid, userPkid);
    }

    @ApiOperation(value = "查询社团成员", notes = "查询社团成员")
    @GetMapping("/searchClubUsers")
    public List<UserDTO> searchClubUsers(@RequestParam String clubPkid) {
        return clubService.searchClubUsers(clubPkid);
    }

    @ApiOperation(value = "同意加入社团", notes = "同意加入社团")
    @GetMapping("/agreeJoinClub")
    public void agreeJoinClub(@RequestParam String messagePkid) {
        clubService.agreeJoinClub(messagePkid);
    }

    @ApiOperation(value = "退出社团", notes = "退出社团")
    @GetMapping("/exitClub")
    public void exitClub(@RequestParam String clubPkid) {
        clubService.exitClub(clubPkid);
    }

    @ApiOperation(
            value = "判断用户当前社团的关系",
            notes = "判断用户当前社团的关系"
    )
    @GetMapping("/judgeClubAndUserRelation")
    @NoCommonResponse
    public CommonResult<Integer> judgeClubAndUserRelation(@RequestParam String clubPkid) {
        BpmConstant bpmConstant = clubService.judgeClubAndUserRelation(clubPkid);
        return new CommonResult<Integer>("0", bpmConstant.getState(), bpmConstant.getValue());
    }

    /**
     * 模糊查询，返回List
     * @param searchParam
     * @return
     */
    @ApiOperation(
            value = "根据条件模糊查询",
            notes = "根据条件模糊查询"
    )
    @GetMapping("/searchByLike")
    public IPage<ClubResDto> searchByLike(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                          @RequestParam(name = "pageSize", defaultValue = "10")
                                                  Integer pageSize,
                                          @RequestParam String searchParam) {
        Page<ClubEntity> page = new Page<>(pageNo, pageSize);
        return service.searchByLike(searchParam, page);
    }

    @ApiOperation(
            value = "查询社团及其分类信息",
            notes = "查询社团及其分类信息"
    )
    @GetMapping("/listClubWithClubCategory")
    public IPage<ClubResDto> listClubWithClubCategory(
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return clubService.listClubWithClubCategory(pageNo, pageSize);
    }
}
