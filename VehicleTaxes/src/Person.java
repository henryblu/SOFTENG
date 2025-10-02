import java.util.Objects;

public final class Person {
    public String licenseNumber, name, surname, address;

    public Person(String licenseNumber, String name, String surname, String address) {
        this.licenseNumber = Objects.requireNonNull(licenseNumber).trim();
        this.name = name;
        this.surname = surname;
        this.address = address;
    }
}
