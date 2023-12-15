package fr.isima.master1.genielog.domain;

import java.util.Arrays;
import java.util.Objects;

public abstract class RoleOperator implements RoleExpression {

    protected RoleExpression operands[];

    protected RoleOperator(RoleExpression ... operands) {
        if ( operands == null ) throw new IllegalArgumentException();
        if ( Arrays.stream(operands).anyMatch(Objects::isNull) ) throw new IllegalArgumentException();
        this.operands = operands;
    }
}
