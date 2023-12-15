package fr.isima.master1.genielog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class NotRoleOperatorTest {

    @Test
    void cannotCreateWithNullOperand() {
        assertThrows(IllegalArgumentException.class, () -> new NotRoleOperator(null));
    }

    @Test
    void mustReturnCorrectStringitifcation() {
        assertThat(new NotRoleOperator(newRoleTerm("dev"))
                .stringify()).isEqualTo("(!dev)");
    }

    @Test
    void mustReturnFalsyPredicateWhenUserHasRole() {
        var expression = new NotRoleOperator(newRoleTerm("dev"));
        var userRoles = UserRoles.of("dev");
        assertThat(expression.toPredicate().test(userRoles)).isFalse();
    }

    @Test
    void mustReturnTruthyPredicateWhenUserHasNotRole() {
        var expression = new NotRoleOperator(newRoleTerm("dev"));
        var userRoles = UserRoles.of("admin");
        assertThat(expression.toPredicate().test(userRoles)).isTrue();
    }

    private RoleExpression newRoleTerm(String roleName) {
        return new RoleTerm(new Role(roleName));
    }

}