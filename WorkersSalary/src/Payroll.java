import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Payroll {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Employee> listEmployees() {
        return List.copyOf(employees);
    }

    public BigDecimal totalSalaries() {
        BigDecimal sum = BigDecimal.ZERO;
        for (Employee e : employees) {
            sum = sum.add(e.totalSalary());
        }
        return sum;
    }
}
