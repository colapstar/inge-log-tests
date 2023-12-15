package fr.isima.master1.genielog.domain;

import java.util.function.Predicate;

public class AndRoleOperator extends RoleOperator {
    public AndRoleOperator(RoleExpression left, RoleExpression right) {
        super(left, right);
    }

    @Override
    public Predicate<UserRoles> toPredicate() {
        return operands[0].toPredicate().and(operands[1].toPredicate());
    }

    @Override
    public String stringify() {
        return "(" + operands[0].stringify() + " & " + operands[1].stringify() + ")";
    }


















}
