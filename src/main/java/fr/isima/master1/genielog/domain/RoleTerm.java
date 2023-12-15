package fr.isima.master1.genielog.domain;

import java.util.function.Predicate;

public class RoleTerm implements RoleExpression {

    private final Role role;

    public RoleTerm(Role role) {
        if ( role == null ) throw new IllegalArgumentException();
        this.role = role;
    }

    @Override
    public Predicate<UserRoles> toPredicate() {
        return userRoles -> userRoles.contains(role);
    }

    @Override
    public String stringify() {
        return role.name;
    }
}
