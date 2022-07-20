package priv.naj.club.common.util;

import java.util.UUID;

public class JavaUUIDGenerator implements UUIDGenerator{
    @Override
    public String newUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Long newID() {
        throw new RuntimeException("not support");
    }
}
