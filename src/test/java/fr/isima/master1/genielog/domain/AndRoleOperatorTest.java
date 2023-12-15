package fr.isima.master1.genielog.domain;

import static org.junit.jupiter.api.Assertions.*;

class AndRoleOperatorTest implements BinaryRoleOperatorTest<AndRoleOperator> {

    @Override
    public AndRoleOperator getInstance(RoleExpression left, RoleExpression right) {
        return new AndRoleOperator(left, right);
    }

    @Override
    public char getSymbol() {
        return '&';
    }

    @Override
    public boolean getTruthTableWhenBothOperandsAreTruthy() {
        return true;
    }

    @Override
    public boolean getTruthTableWhenOnlyLeftOperantIsTruthy() {
        return false;
    }

    @Override
    public boolean getTruthTableWhenOnlyRightOperandIsTruthy() {
        return false;
    }

    @Override
    public boolean getTruthTableWhenNoOperandIsTruthy() {
        return false;
    }
}