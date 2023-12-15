package fr.isima.master1.genielog.domain;

import java.util.function.Predicate;

public interface RoleExpression {
    Predicate<UserRoles> toPredicate();

    String stringify();
}
