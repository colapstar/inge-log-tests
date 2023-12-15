package fr.isima.master1.genielog.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface EqualityTests<T> {

    T getInstance();
    T getEqualInstance();

    T getDifferentInstance();

    @Test
    default void mustBeEqualToItSelf() {
        T object = getInstance();
        assertThat(object.equals(object)).isTrue();
    }

    @Test
    default void cannotBeEqualToNull() {
        assertThat(getInstance().equals(null)).isFalse();
    }

    @Test
    default void cannotBeEqualToSomethingElse() {
        assertThat(getInstance().equals("")).isFalse();
    }

    @Test
    default void mustHaveSameHashCodeForEqualObjects() {
        assertThat(getInstance().hashCode()).isEqualTo(getEqualInstance().hashCode());
    }

    @Test
    default void mustBeEqualToASameObejct() {
        assertThat(getInstance().equals(getEqualInstance())).isTrue();
    }

    @Test
    default void cannotBeEqualToADifferentObject() {
        assertThat(getInstance().equals(getDifferentInstance())).isFalse();
    }

    @Test
    default void mustHaveConsistentHashCode() {
        var object = getInstance();
        assertThat(object.hashCode()).isEqualTo(object.hashCode());
    }

}
