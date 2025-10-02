import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Motorcycle extends Vehicle {
    public int engineDisplacement;

    public Motorcycle(String plate, String maker, String model, int engineDisplacement) {
        super(plate, maker, model);
        this.engineDisplacement = engineDisplacement;
    }

    public BigDecimal calculateTax() {
        BigDecimal displacement = BigDecimal.valueOf(engineDisplacement);
        BigDecimal rate = new BigDecimal("0.10");
        BigDecimal rawTax = displacement.multiply(rate);
        return rawTax.setScale(2, RoundingMode.HALF_UP);
    }
}
