package priv.naj.club.system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.ApiOperation;
import priv.naj.club.system.controller.dto.NoticeDTO;
import priv.naj.club.system.entity.NoticeEntity;
import priv.naj.club.system.service.NoticeService;

import org.springframework.web.bind.annotation.RestController;

import priv.naj.club.common.CommonAbstractController;

/**
 * <p>
 * 社团公告表 前端控制器
 * </p>
 *
 * @author nieaojie
 */
@RestController
@RequestMapping("/system/noticeEntity")
public class NoticeController extends CommonAbstractController<NoticeEntity, NoticeService> {

    @ApiOperation(value = "查询社团发布的公告", notes = "查询社团发布的公告")
    @GetMapping({ "/getClubAllNotice" })
    public List<NoticeDTO> getClubAllNotice(String clubPkid,Integer status) {
        return service.getClubAllNotice(clubPkid,status);
    }
}
