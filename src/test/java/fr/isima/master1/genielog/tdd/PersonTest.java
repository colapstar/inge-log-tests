package fr.isima.master1.genielog.tdd;

import fr.isima.master1.genielog.tdd.Person;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class PersonTest {

    @Nested
    class Construction {
        @Test
        void cannotCreatePersonWithNullLastName() {
            try {
                new Person(null);
                fail();
            } catch (IllegalArgumentException iae) {
                // We actually expected this exception here
            }
        }

        @Test
        void cannotCreatePersonWithEmptyLastName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Person(""));
        }

        @Test
        void cannotCreatePersonWithBlankLastName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Person("    "));
        }

        @Test
        void cannotCreatePersonWithLowercaseLettersInLastName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Person("Toto"));
        }

        @Test
        void cannotCreatePersonWithDigitsInLastName() {
            assertThrows(IllegalArgumentException.class,
                    () -> new Person("TOTO63"));
        }

        @Test
        void mustCreatePersonWithValidLastName() {
            new Person("TOTO");
        }
    }


    @Test
    void mustReturnItsProvidedLastName() {
        assertThat(new Person("TITI").getLastName())
                .isEqualTo("TITI");
        assertThat(new Person("TOTO").getLastName())
                .isEqualTo("TOTO");

    }

    @Nested
    class HashcodeAndEquals {
        @Test
        void cannotBeEqualsToNull() {
            var p = new Person("TITI");
            assertThat(p.equals(null)).isFalse();
        }

        @Test
        void mustBeEqualsToItself() {
            var p = new Person("TITI");
            assertThat(p.equals(p)).isTrue();
        }

        @Test
        void cannotBeEqualsToSomethingElse() {
            var p = new Person("TITI");
            assertThat(p.equals("TITI")).isFalse();
        }

        @Test
        void mustBeEqualsToAnotherPersonWithSameLastName() {
            var p = new Person("TITI");
            var p2 = new Person("TITI");
            assertThat(p.equals(p2)).isTrue();
        }

        @Test
        void cannotBeEqualsToAnotherPersonWithDifferentLastName() {
            var p = new Person("TITI");
            var p2 = new Person("TOTO");
            assertThat(p.equals(p2)).isFalse();
        }

        @Test
        void mustHaveSameHashcodeAsIdenticalPerson() {
            var p = new Person("TITI");
            var p2 = new Person("TITI");
            assertThat(p.hashCode()).isEqualTo(p2.hashCode());
        }

        @Test
        void mustHaveConsistentHashcode() {
            var p = new Person("TITI");
            assertThat(p.hashCode()).isEqualTo(p.hashCode());
        }
    }

















}














