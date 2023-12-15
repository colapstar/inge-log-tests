package fr.isima.master1.genielog.domain;

public class Feature {

    public final String name;

    public RoleExpression expression;

    public Feature(String name) {
        if ( name == null || name.isBlank() ) throw new IllegalArgumentException();
        this.name = name;
    }
    public Feature(String name, RoleExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    public boolean isEnabled(UserRoles userRoles) {
        return expression == null || expression.toPredicate().test(userRoles);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feature feature = (Feature) o;

        return name.equals(feature.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
