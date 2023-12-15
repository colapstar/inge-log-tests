package fr.isima.master1.genielog.domain;

public class Role {
    public final String name;
    public Role(String name) {
        if ( name == null || name.isBlank() )
            throw new IllegalArgumentException("Cannot create a role without name");
        if ( !name.matches("[A-Za-z0-9_]+"))
            throw new IllegalArgumentException("Invalid role name : \"" + name + "\"");
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name.equalsIgnoreCase(role.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
