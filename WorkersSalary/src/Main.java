public class Main {

    public static void main(String[] args) {
        SalaryInfo payroll = new SalaryInfo();

        payroll.addEmployee(new Employee("Bob Jones", 1000, 10));
        payroll.addEmployee(new Employee("Joe", 10020, 40));

        for (var e : payroll.listEmployees()) {
            System.out.println(e);
        }

        System.out.println("Total salaries: " + payroll.totalSalaries());
    }
}
