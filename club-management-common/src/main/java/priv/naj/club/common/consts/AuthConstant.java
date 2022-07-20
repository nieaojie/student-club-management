package priv.naj.club.common.consts;

public interface AuthConstant {
    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;

    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final Integer ACT_SYNC_1 = 1;
    public static final Integer ACT_SYNC_0 = 0;

    String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * 社团状态（0-冻结，1-启用）
     */
    public static final Integer CLUB_FREEZE = 0;
    public static final Integer CLUB_UNFREEZE = 1;
}
