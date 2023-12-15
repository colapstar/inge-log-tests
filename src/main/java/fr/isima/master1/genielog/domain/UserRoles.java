package fr.isima.master1.genielog.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserRoles {

    private final Collection<Role> roles;

    private UserRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public boolean contains(Role role) {
        return roles.contains(role);
    }

    public static UserRoles of(Role ... roles ) {
        if ( roles == null ) throw new IllegalArgumentException();
        return new UserRoles(Arrays.stream(roles)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    public static UserRoles of(String ... roleNames) {
        if ( roleNames == null ) throw new IllegalArgumentException();
        return new UserRoles(Arrays.stream(roleNames)
                .filter(roleName -> roleName != null && !roleName.isBlank())
                .map(Role::new)
                .collect(Collectors.toList()));
    }





















}
