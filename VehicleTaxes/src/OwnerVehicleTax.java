import java.math.BigDecimal;

public final class OwnerVehicleTax {
    public final Vehicle vehicle;
    public final BigDecimal tax;

    public OwnerVehicleTax(Vehicle v) {
        this.vehicle = v;
        this.tax = v.calculateTax();
    }
}
