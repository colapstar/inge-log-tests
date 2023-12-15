package fr.isima.master1.genielog.tdd;

public class Person {

    private final String lastName;

    public Person(String lastName) {
        validateLastName(lastName);

        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return lastName.equals(person.lastName);
    }

    @Override
    public int hashCode() {
        return lastName.hashCode();
    }


    private void validateLastName(String lastName) {
        if (lastName == null
                || lastName.isBlank()
                || lastName.matches(".*[a-z0-9].*")
        ) throw new IllegalArgumentException();
    }
}
