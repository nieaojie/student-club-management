package priv.naj.club.common.consts;

public interface AuthCacheConstant {
    /**
     * 字典信息缓存
     */
    String SYS_DICT_CACHE = "sys:cache:dict";
    /**
     * 表字典信息缓存
     */
    String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";
    String SYS_DICT_TABLE_BY_KEYS_CACHE = SYS_DICT_TABLE_CACHE + "ByKeys";

    /**
     * 数据权限配置缓存
     */
    String SYS_DATA_PERMISSIONS_CACHE = "sys:cache:permission:datarules";

    /**
     * 缓存用户信息
     */
    String SYS_USERS_CACHE = "sys:cache:user";

    /**
     * 全部部门信息缓存
     */
    String SYS_DEPARTS_CACHE = "sys:cache:depart:alldata";

    /**
     * 全部部门ids缓存
     */
    String SYS_DEPART_IDS_CACHE = "sys:cache:depart:allids";
    String PREFIX_USER_TOKEN = "prefix_user_token_";
}
