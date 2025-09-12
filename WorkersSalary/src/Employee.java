import java.math.BigDecimal;


public class Employee {
    private String name;
    private BigDecimal base_salary;
    private BigDecimal complement;

    public Employee(String name, int salary, int complement) {
        this.name = name;
        this.base_salary = new BigDecimal(salary);
        this.complement = new BigDecimal(complement);
    }
    
    public String name() { 
        return name;
    }
    public BigDecimal base_salary() { 
        return base_salary;
    }
    public BigDecimal complement() { 
        return complement;
    }
    public BigDecimal totalSalary() { 
        return base_salary.add(complement);
    }
    
    public String toString() {
        return "Employee{name='" + name + "', base=" + base_salary + ", complement=" + complement + ", total=" + totalSalary() + "}";
    }
}