package fr.isima.master1.genielog.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
class RoleTest {


    @Nested
    class Construction {

        @Test
        void cannotCreateRoleWithNullName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Role(null));
        }

        @Test
        void cannotCreateRoleWithEmptyName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Role(""));
        }

        @Test
        void cannotCreateRoleWithBlankName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Role("    "));
        }

        @Test
        void canCreateRoleAlphaNumDigitsAndUnderscoreName() {
            new Role("DevOps_42");
        }

        @Test
        void canCreateRoleSpecialCharactersInName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Role("Dev&admin-!"));
        }
    }

    @Nested
    class Attributes {
        @Test
        void mustHaveItsNameAsProvided() {
            assertThat(new Role("Dev").name).isEqualTo("Dev");
        }
    }

    @Nested
    class EqualsAndHashCode implements EqualityTests<Role>{

        @Override
        public Role getInstance() {
            return new Role("dev");
        }

        @Override
        public Role getEqualInstance() {
            return new Role("DeV");
        }

        @Override
        public Role getDifferentInstance() {
            return new Role("admin");
        }

    }
}