import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Car extends Vehicle {
    public FuelType fuelType;
    public int co2PerKm;

    public Car(String plate, String maker, String model, FuelType fuelType, int co2PerKm) {
        super(plate, maker, model);
        this.fuelType = fuelType;
        this.co2PerKm = co2PerKm;
    }

    public BigDecimal calculateTax() {
        BigDecimal rate = switch (fuelType) {
            case PETROL -> new BigDecimal("1.4");
            case DIESEL -> new BigDecimal("1.8");
            case HYBRID -> new BigDecimal("1.2");
        };
        return rate.multiply(BigDecimal.valueOf(co2PerKm)).setScale(2, RoundingMode.HALF_UP);
    }
}
