package priv.naj.club.common.consts;

public enum MessageStatusConstant {
    UNREAD("未读", 0),
    READ("已读", 1),
    ALL("全部", 2),
    ;
    private String state;
    private Integer value;

    MessageStatusConstant(String state, Integer value) {
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
