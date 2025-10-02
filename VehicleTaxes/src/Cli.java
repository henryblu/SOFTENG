import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class Cli {
    private final VehicleTaxApplication app;

    public Cli(VehicleTaxApplication app) { this.app = app; }

    public void run() {
        printWelcome();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) break;
            if (line.equalsIgnoreCase("help")) { printHelp(); continue; }

            String[] args = line.split("\\s+");
            String cmd = args[0].toLowerCase(Locale.ROOT);

            try {
                switch (cmd) {
                    case "add-person" -> {
                        requireAtLeast(args, 5);
                        app.createPerson(new Person(args[1], args[2], args[3], rest(args, 4)));
                        ok();
                    }
                    case "edit-person" -> {
                        requireAtLeast(args, 5);
                        app.editPerson(new Person(args[1], args[2], args[3], rest(args, 4)));
                        ok();
                    }
                    case "del-person" -> {
                        requireExactly(args, 2);
                        app.deletePerson(args[1]);
                        ok();
                    }
                    case "add-car" -> {
                        requireAtLeast(args, 6);
                        FuelType ft = FuelType.valueOf(args[4].toUpperCase(Locale.ROOT));
                        int co2 = parseInt(args[5]);
                        app.createVehicle(new Car(args[1], args[2], args[3], ft, co2));
                        ok();
                    }
                    case "add-moto" -> {
                        requireAtLeast(args, 5);
                        int cc = parseInt(args[4]);
                        app.createVehicle(new Motorcycle(args[1], args[2], args[3], cc));
                        ok();
                    }
                    case "edit-car" -> {
                        requireAtLeast(args, 6);
                        Vehicle base = findVehicleOrThrow(args[1]);
                        if (!(base instanceof Car)) throw new IllegalArgumentException("Not a car");
                        FuelType ft = FuelType.valueOf(args[4].toUpperCase(Locale.ROOT));
                        int co2 = parseInt(args[5]);
                        app.editVehicle(new Car(args[1], args[2], args[3], ft, co2));
                        ok();
                    }
                    case "edit-moto" -> {
                        requireAtLeast(args, 5);
                        Vehicle base = findVehicleOrThrow(args[1]);
                        if (!(base instanceof Motorcycle)) throw new IllegalArgumentException("Not a motorcycle");
                        int cc = parseInt(args[4]);
                        app.editVehicle(new Motorcycle(args[1], args[2], args[3], cc));
                        ok();
                    }
                    case "del-vehicle" -> {
                        requireExactly(args, 2);
                        app.deleteVehicle(args[1]);
                        ok();
                    }
                    case "transfer" -> {
                        requireExactly(args, 3);
                        app.transferOwnership(args[1], args[2].equalsIgnoreCase("none") ? null : args[2]);
                        ok();
                    }
                    case "calc-tax" -> {
                        requireExactly(args, 2);
                        System.out.println("Tax: " + app.calculateTax(args[1]) + " €");
                    }
                    case "annual" -> {
                        requireExactly(args, 1);
                        printAnnual(app.annualListing());
                    }
                    case "search-persons" -> {
                        requireAtLeast(args, 2);
                        String q = rest(args, 1);
                        app.searchPersons(q).forEach(p ->
                                System.out.printf("%s  %s %s  [%s]%n",
                                        p.licenseNumber, p.name, p.surname, p.address));
                    }
                    case "search-vehicles" -> {
                        requireAtLeast(args, 2);
                        String q = rest(args, 1);
                        app.searchVehicles(q).forEach(Cli::printVehicleLine);
                    }
                    case "list-persons" -> {
                        requireExactly(args, 1);
                        app.allPersons().forEach(p ->
                                System.out.printf("%s  %s %s  [%s]%n",
                                        p.licenseNumber, p.name, p.surname, p.address));
                    }
                    case "list-vehicles" -> {
                        requireExactly(args, 1);
                        app.allVehicles().forEach(Cli::printVehicleLine);
                    }
                    default -> System.out.println("Unknown command");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        System.out.println("Bye.");
    }

    private Vehicle findVehicleOrThrow(String plate) {
        for (Vehicle v : app.allVehicles()) if (v.plate.equals(plate)) return v;
        throw new NoSuchElementException("No vehicle");
    }

    private static int parseInt(String s) {
        int v = Integer.parseInt(s);
        if (v < 0) throw new IllegalArgumentException("Must be >= 0");
        return v;
    }

    private static void requireExactly(String[] a, int n) {
        if (a.length != n) throw new IllegalArgumentException("Expected " + n + " args");
    }

    private static void requireAtLeast(String[] a, int n) {
        if (a.length < n) throw new IllegalArgumentException("Expected at least " + n + " args");
    }

    private static String rest(String[] a, int from) {
        StringBuilder sb = new StringBuilder();
        for (int i = from; i < a.length; i++) {
            if (i > from) sb.append(' ');
            sb.append(a[i]);
        }
        return sb.toString();
    }

    private static void ok() { System.out.println("OK"); }

    private static void printWelcome() {
        System.out.println("Vehicle Tax System — type 'help' for commands, 'quit' to exit");
    }

    private static void printHelp() {
        System.out.println("""
      Commands:

        add-person <license> <name> <surname> <address...>
            Create a new person in the system.

        edit-person <license> <name> <surname> <address...>
            Update an existing person. License number must already exist.

        del-person <license>
            Remove a person. Any vehicles they owned will become unowned.

        add-car <plate> <maker> <model> <fuelType> <co2>
            Add a car. fuelType = PETROL | DIESEL | HYBRID.

        add-moto <plate> <maker> <model> <cc>
            Add a motorcycle. cc = engine displacement.

        edit-car <plate> <maker> <model> <fuelType> <co2>
        edit-moto <plate> <maker> <model> <cc>
            Update a vehicle by plate. Keeps current owner.

        del-vehicle <plate>
            Remove a vehicle.

        transfer <plate> <toLicense|none>
            Transfer vehicle ownership to a person (license number) or 'none' to clear owner.

        calc-tax <plate>
            Calculate the annual tax for a single vehicle.

        annual
            Generate the yearly tax listing. Includes owners with no vehicles and unowned vehicles.

        search-persons <query...>
            Search people by license, name, surname, or address.

        search-vehicles <query...>
            Search vehicles by plate, maker, or model.

        list-persons
            Show all persons.

        list-vehicles
            Show all vehicles.

        help
            Show this help message.

        quit
            Exit the program.
    """);
    }


    private static void printVehicleLine(Vehicle v) {
        String owner = (v.owner == null) ? "(no owner)" : v.owner.licenseNumber;
        if (v instanceof Car c) {
            System.out.printf("%s  CAR  %s %s  %s  CO2=%dg/km  owner=%s%n",
                    v.plate, v.maker, v.model, c.fuelType, c.co2PerKm, owner);
        } else if (v instanceof Motorcycle m) {
            System.out.printf("%s  MOTO %s %s  %dcc  owner=%s%n",
                    v.plate, v.maker, v.model, m.engineDisplacement, owner);
        } else {
            System.out.printf("%s  %s %s  owner=%s%n", v.plate, v.maker, v.model, owner);
        }
    }

    private static void printAnnual(List<OwnerTaxSummary> list) {
        for (OwnerTaxSummary s : list) {
            String header = (s.owner == null) ? "No Owner" :
                    s.owner.licenseNumber + " - " + s.owner.name + " " + s.owner.surname;
            System.out.println("== " + header + " ==");
            if (s.items.isEmpty()) {
                System.out.println("(no vehicles)");
            } else {
                for (OwnerVehicleTax i : s.items) {
                    Vehicle v = i.vehicle;
                    String kind = (v instanceof Car) ? "CAR" : "MOTO";
                    System.out.printf(" - %s %s %s (%s) : %s €%n",
                            v.plate, v.maker, v.model, kind, i.tax);
                }
            }
            System.out.println("TOTAL: " + s.total + " €\n");
        }
    }
}
