import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static DatabaseManager db = new DatabaseManager();
    static Garage garage;

    public static void main(String[] args) {
        garage = db.loadGarage();
        System.out.println("Loaded " + garage.getAll().size() + " motorcycle(s) from database.");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addmotorcycle(); break;
                case "2": logService(); break;
                case "3": viewHistory(); break;
                case "4": checkOverdue(); break;
                case "5": listGarage(); break;
                case "6": running = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    static void printMenu() {
        System.out.println("\n=== Motorcycle Maintenance Tracker ===");
        System.out.println("1. Add Motorcycle");
        System.out.println("2. Log Service");
        System.out.println("3. View Service History");
        System.out.println("4. Check what's due");
        System.out.println("5. List All Motorcycles");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    static void addmotorcycle() {
        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Current Mileage: ");
        int mileage = Integer.parseInt(scanner.nextLine());

        Motorcycle m = new Motorcycle(make, model, year, mileage);
        int id = db.saveMotorcycle(m);
        m.setId(id);
        garage.addMotorcycle(m);

        System.out.println("Added: " + m);
    }

    static Motorcycle selectMotorcycle() {
        if (garage.getAll().isEmpty()) {
            System.out.println("No motorcycles yet. Add one first.");
            return null;
        }
        listGarage();
        System.out.print("Enter model name: ");
        String model = scanner.nextLine();
        Motorcycle m = garage.getMotorcycleByModel(model);
        if (m == null) {
            System.out.println("Motorcycle not found.");
        }
        return m;
    }

    static void logService() {
        Motorcycle m = selectMotorcycle();
        if (m == null) return;

        System.out.println("Service Type: ");
        MaintenanceType[] types = MaintenanceType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }
        int typeChoice = Integer.parseInt(scanner.nextLine()) - 1;
        MaintenanceType type = types[typeChoice];

        System.out.print("Mileage at service: ");
        int mileage = Integer.parseInt(scanner.nextLine());

        System.out.print("Notes: ");
        String notes = scanner.nextLine();

        MaintenanceRecord record = new MaintenanceRecord(type, LocalDate.now(), mileage, notes);
        m.addRecord(record);
        db.saveRecord(m.getId(), record);

        if (mileage > m.getCurrentMileage()) {
            m.setCurrentMileage(mileage);
            db.updateMileage(m);
        }

        System.out.println("Logged: " + record);
    }

    static void viewHistory() {
        Motorcycle m = selectMotorcycle();
        if (m == null) return;

        List<MaintenanceRecord> records = m.getAllRecords();
        if (records.isEmpty()) {
            System.out.println("No service history yet.");
            return;
    }
    for (MaintenanceRecord r : records) {
            System.out.println(r);
        }
    }

    static void checkOverdue() {
        Motorcycle m = selectMotorcycle();
        if (m == null) return;

        List<String> overdue = m.getOverdueItems();
        if (overdue.isEmpty()) {
            System.out.println("Nothing overdue. You're all caught up!");
        } else {
            for (String item : overdue) {
                System.out.println(item);
            }
        }
    }

    static void listGarage() {
        garage.listAll();
    }
}    