package priv.naj.club.common.enums;

public enum ClientType implements BaseEnum {
    web(1, "web"),
    android(2, "android");

    private Integer id;
    private String name;

    private ClientType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
