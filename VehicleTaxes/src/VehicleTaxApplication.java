import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public final class VehicleTaxApplication {
    private final Map<String, Person> persons = new HashMap<>();
    private final Map<String, Vehicle> vehicles = new HashMap<>();

    public void createPerson(Person p) {
        if (persons.containsKey(p.licenseNumber))
            throw new IllegalArgumentException("Person exists: " + p.licenseNumber);
        persons.put(p.licenseNumber, p);
    }

    public void editPerson(Person p) {
        if (!persons.containsKey(p.licenseNumber))
            throw new NoSuchElementException("No person: " + p.licenseNumber);
        persons.put(p.licenseNumber, p);
    }

    public void deletePerson(String license) {
        if (persons.remove(license) == null) throw new NoSuchElementException("No person: " + license);
        // Clear ownerships
        vehicles.values().forEach(v -> {
            if (v.owner != null && v.owner.licenseNumber.equals(license)) v.owner = null;
        });
    }

    public void createVehicle(Vehicle v) {
        if (vehicles.containsKey(v.plate))
            throw new IllegalArgumentException("Vehicle exists: " + v.plate);
        vehicles.put(v.plate, v);
    }

    public void editVehicle(Vehicle v) {
        Vehicle old = vehicles.get(v.plate);
        if (old == null) throw new NoSuchElementException("No vehicle: " + v.plate);
        v.owner = old.owner;
        vehicles.put(v.plate, v);
    }

    public void deleteVehicle(String plate) {
        if (vehicles.remove(plate) == null)
            throw new NoSuchElementException("No vehicle: " + plate);
    }

    public void transferOwnership(String plate, String toLicenseNullable) {
        Vehicle v = vehicles.get(plate);
        if (v == null) throw new NoSuchElementException("No vehicle: " + plate);
        if (toLicenseNullable == null || toLicenseNullable.isBlank()) {
            v.owner = null;
        } else {
            Person p = persons.get(toLicenseNullable);
            if (p == null) throw new NoSuchElementException("No person: " + toLicenseNullable);
            v.owner = p;
        }
    }

    public BigDecimal calculateTax(String plate) {
        Vehicle v = vehicles.get(plate);
        if (v == null) throw new NoSuchElementException("No vehicle: " + plate);
        return v.calculateTax();
    }

    public List<OwnerTaxSummary> annualListing() {
        List<OwnerTaxSummary> summaries = new ArrayList<>();

        List<Person> people = new ArrayList<>(persons.values());
        people.sort(Comparator.comparing(p -> p.licenseNumber));

        for (Person p : people) {
            List<OwnerVehicleTax> items = new ArrayList<>();
            for (Vehicle v : vehicles.values()) {
                if (p.equals(v.owner)) {
                    items.add(new OwnerVehicleTax(v));
                }
            }
            items.sort(Comparator.comparing(vt -> vt.vehicle.plate));
            summaries.add(new OwnerTaxSummary(p, items));
        }

        List<OwnerVehicleTax> unowned = new ArrayList<>();
        for (Vehicle v : vehicles.values()) {
            if (v.owner == null) {
                unowned.add(new OwnerVehicleTax(v));
            }
        }
        unowned.sort(Comparator.comparing(vt -> vt.vehicle.plate));

        if (!unowned.isEmpty()) {
            summaries.add(new OwnerTaxSummary(null, unowned));
        }

        return summaries;
    }


    // Search
    public List<Person> searchPersons(String q) {
        String s = q.toLowerCase(Locale.ROOT);
        List<Person> results = new ArrayList<>();

        for (Person p : persons.values()) {
            if (p.licenseNumber.toLowerCase().contains(s) ||
                    n(p.name).contains(s) ||
                    n(p.surname).contains(s) ||
                    n(p.address).contains(s)) {
                results.add(p);
            }
        }

        results.sort(Comparator.comparing(p -> p.licenseNumber));
        return results;
    }


    public List<Vehicle> searchVehicles(String q) {
        String s = q.toLowerCase(Locale.ROOT);
        List<Vehicle> results = new ArrayList<>();

        for (Vehicle v : vehicles.values()) {
            if (v.plate.toLowerCase().contains(s) ||
                    n(v.maker).contains(s) ||
                    n(v.model).contains(s)) {
                results.add(v);
            }
        }

        results.sort(Comparator.comparing(v -> v.plate));
        return results;
    }


    // For CLI
    public Collection<Person> allPersons() { return persons.values(); }
    public Collection<Vehicle> allVehicles() { return vehicles.values(); }

    private static String n(String s) { return s == null ? "" : s.toLowerCase(Locale.ROOT); }
}
