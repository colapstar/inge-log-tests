package fr.isima.master1.genielog.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public interface BinaryRoleOperatorTest<T extends RoleOperator> {

    T getInstance(RoleExpression left, RoleExpression right);
    char getSymbol();

    boolean getTruthTableWhenBothOperandsAreTruthy();
    boolean getTruthTableWhenOnlyLeftOperantIsTruthy();
    boolean getTruthTableWhenOnlyRightOperandIsTruthy();
    boolean getTruthTableWhenNoOperandIsTruthy();



    final RoleTerm right = new RoleTerm(new Role("right"));
    final RoleTerm left = new RoleTerm(new Role("left"));

    @Test
    default void cannotCreateWithNullOperands() {
        assertThrows(IllegalArgumentException.class,
                () -> getInstance(null, null));
    }
    @Test
    default void cannotCreateWithNullLeftOperand() {
        assertThrows(IllegalArgumentException.class,
                () -> getInstance(null, right));
    }
    @Test
    default void cannotCreateWithNullRightOperands() {
        assertThrows(IllegalArgumentException.class,
                () -> getInstance(left, null));
    }

    @Test
    default void mustCreateWithNonNullOperands() {
        getInstance(left, right);
    }

    @Test
    default void mustHaveCorrectStringification() {
        assertThat(getInstance(left, right).stringify())
                .isEqualTo("(left " + getSymbol() + " right)");
    }

    @Test
    default void mustHaveCorrectTruthTable() {
        var predicate = getInstance(left, right).toPredicate();
        assertThat(predicate.test(UserRoles.of("left", "right"))).isEqualTo(getTruthTableWhenBothOperandsAreTruthy());
        assertThat(predicate.test(UserRoles.of("left", ""))).isEqualTo(getTruthTableWhenOnlyLeftOperantIsTruthy());
        assertThat(predicate.test(UserRoles.of("", "right"))).isEqualTo(getTruthTableWhenOnlyRightOperandIsTruthy());
        assertThat(predicate.test(UserRoles.of("", ""))).isEqualTo(getTruthTableWhenNoOperandIsTruthy());
    }

}
