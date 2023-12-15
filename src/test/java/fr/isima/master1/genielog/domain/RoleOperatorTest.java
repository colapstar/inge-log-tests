package fr.isima.master1.genielog.domain;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class RoleOperatorTest {

    class DummyRoleOperator extends RoleOperator {
        public DummyRoleOperator(RoleExpression... operands) {
            super(operands);
        }

        @Override
        public Predicate<UserRoles> toPredicate() {
            return null;
        }

        @Override
        public String stringify() {
            return null;
        }
    }

    @Test
    void cannotCreateRoleOperatorWithNullArray() {
        assertThrows(IllegalArgumentException.class,
                () -> new DummyRoleOperator((RoleExpression[]) null));
    }

    @Test
    void cannotCreateRoleOperatorWithNullInArray() {
        assertThrows(IllegalArgumentException.class,
                () -> new DummyRoleOperator(null));
    }

    @Test
    void mustRetainItsOperands() {
        var right = new RoleTerm(new Role("dev"));
        var left = new RoleTerm(new Role("admin"));
        var operator = new DummyRoleOperator(left, right);
        assertThat(operator.operands).isNotEmpty();
        assertThat(operator.operands).containsExactly(left, right);
    }
}