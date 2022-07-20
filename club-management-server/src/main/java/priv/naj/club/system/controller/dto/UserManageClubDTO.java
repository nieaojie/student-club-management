package priv.naj.club.system.controller.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserManageClubDTO {
    List<UserDTO> userDTOS = new ArrayList<>();
    ClubDetailDTO clubDetailDTO;
}
