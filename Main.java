import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Garage garage = new Garage();

    Motorcycle zx6r = new Motorcycle("Kawasaki", "ZX-6R", 2018, 10000);
    zx6r.setCustomInterval(MaintenanceType.CHAIN_LUBE, 400);

    garage.addMotorcycle(zx6r);

    zx6r.addRecord(new MaintenanceRecord(MaintenanceType.OIL_CHANGE, LocalDate.of(2026, 5, 10), 9500, "Full synthetic, Motul"));
    zx6r.addRecord(new MaintenanceRecord(MaintenanceType.CHAIN_LUBE, LocalDate.of(2026, 8 , 1), 9800, "After track day"));

    System.out.println("--- Garage ---");
    garage.listAll();

    System.out.println("\n--- ZX-6R overdue items ---");
    for (String item : zx6r.getOverdueItems()) {
        System.out.println(item);
    }
    }
}