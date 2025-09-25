public class Person implements Comparable<Person> {
    private final String firstName;
    private final String surname;

    public Person(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    @Override
    public int compareTo(Person other) {
        int cmp = this.surname.compareTo(other.surname);
        if (cmp == 0) {
            cmp = this.firstName.compareTo(other.firstName);
        }
        return cmp;
    }

    public void printName() {
        System.out.println(getFullName());
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getFullName() {
        return this.firstName + " " + this.surname;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
