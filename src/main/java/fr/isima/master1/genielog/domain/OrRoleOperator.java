package fr.isima.master1.genielog.domain;

import java.util.function.Predicate;

public class OrRoleOperator extends RoleOperator {
    public OrRoleOperator(RoleExpression left, RoleExpression right) {
        super(left, right);
    }

    @Override
    public Predicate<UserRoles> toPredicate() {
        return operands[0].toPredicate().or(operands[1].toPredicate());
    }
    
    @Override
    public String stringify() {
        return "(" + operands[0].stringify() + " | " + operands[1].stringify() + ")";
    }


















}
