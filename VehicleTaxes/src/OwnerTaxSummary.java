import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class OwnerTaxSummary {
    public final Person owner;
    public final List<OwnerVehicleTax> items;
    public final BigDecimal total;

    public OwnerTaxSummary(Person owner, List<OwnerVehicleTax> items) {
        this.owner = owner;
        this.items = items;

        BigDecimal sum = BigDecimal.ZERO;
        for (OwnerVehicleTax i : items) {
            sum = sum.add(i.tax);
        }
        this.total = sum.setScale(2, RoundingMode.HALF_UP);
    }
}
