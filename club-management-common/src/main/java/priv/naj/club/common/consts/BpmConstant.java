package priv.naj.club.common.consts;

public enum BpmConstant {
    WAITING_PASS("待审核", 0),
    ALREADY_JOIN("已加入", 1),
    NOT_PASS("未通过", 2),
    NOT_JOIN("未加入", 3),
    ALREADY_EXIT("已退出", 4),
    ;
    private String state;
    private Integer value;

    BpmConstant(String state, Integer value) {
        this.state = state;
        this.value = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
