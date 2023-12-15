package fr.isima.master1.genielog.domain;

import java.util.function.Predicate;

public class NotRoleOperator extends RoleOperator {

    public NotRoleOperator(RoleExpression operand) {
        super(operand);
    }

    @Override
    public Predicate<UserRoles> toPredicate() {
        return operands[0].toPredicate().negate();
    }

    @Override
    public String stringify() {
        return "(!" + operands[0].stringify() + ")";
    }
}
