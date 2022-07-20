package priv.naj.club.common.enums;

import java.util.Objects;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author nieaojie
 */
public interface BaseEnum extends IEnum<Integer> {

    Integer getId();

    String getName();

    default boolean eq(Integer val) {
        return Objects.equals(this.getId(), val);
    }

    default Integer getValue() {return this.getId();}
}
