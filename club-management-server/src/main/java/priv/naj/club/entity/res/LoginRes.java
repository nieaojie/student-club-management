package priv.naj.club.entity.res;

import lombok.Data;
import priv.naj.club.system.controller.dto.UserDTO;

@Data
public class LoginRes {
    private String token;
    private UserDTO userDTO;
}
