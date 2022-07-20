package priv.naj.club.common.consts;

public interface AuthErrorCodeConstant extends ErrorCodeConst {
    String PERMISSION_NOT_FIND = "auth-1";
    String PW_ERROR = "auth-2";
    String PW_EMPTY = "auth-3";
    String PW_NOT_EQUAL = "auth-4";
    String USER_NOT_EXIST = "auth-5";
    String USER_LOGOUT = "auth-6";
    String USER_FREEZE = "auth-7";
    String PW_NOT_CORRECT = "auth-8";
    String SMS_CODE_ERROR = "auth-9";
    String SMS_CODE_EXPIRED = "auth-10";
    String PW_RESET_FAIL = "auth-11";
    String PHONE_MODIFY_FAIL = "auth-12";
    String PHONE_ALREADY_EXIST = "auth-13";
    String MODEL_EMPTY = "auth-14";
    String USERNAME_ALREADY_EXIST = "auth-15";
    String DUPLICATE = "dup-1";
    String QRCODE_INVALID = "qrcode-invalid";
    String QRCODE_USED = "qrcode-used";
}
