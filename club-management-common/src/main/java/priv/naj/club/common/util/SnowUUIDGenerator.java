package priv.naj.club.common.util;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.toolkit.Sequence;

import priv.naj.club.common.util.UUIDGenerator;

@Component
public class SnowUUIDGenerator implements UUIDGenerator {
    private Sequence sequence = new Sequence();

    @Override
    public String newUUID() {
        return this.newID().toString();
    }

    @Override
    public Long newID() {
        return sequence.nextId();
    }
}
