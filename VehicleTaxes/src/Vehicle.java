import java.math.BigDecimal;
import java.util.Objects;

public abstract class Vehicle {
    public String plate, maker, model;
    public Person owner; // nullable

    protected Vehicle(String plate, String maker, String model) {
        this.plate = Objects.requireNonNull(plate).trim();
        this.maker = maker;
        this.model = model;
    }

    public abstract BigDecimal calculateTax();
}
