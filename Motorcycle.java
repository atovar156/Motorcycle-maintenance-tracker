import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Motorcycle {
    private String make;
    private String model;
    private int year;
    private int currentMileage;
    private List<MaintenanceRecord> serviceHistory;
    private Map<MaintenanceType, Integer> customIntervals;

    public Motorcycle(String make, String model, int year, int currentMileage) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.currentMileage = currentMileage;
        this.serviceHistory = new ArrayList<>();
        this.customIntervals = new EnumMap<>(MaintenanceType.class);

    }

    private int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    // Let a bike override the default interval for a given type
    public void setCustomInterval(MaintenanceType type, int miles) {
        customIntervals.put(type, miles);
    }

    private int getIntervalFor(MaintenanceType type) {
        return customIntervals.getOrDefault(type, type.getDefaultIntervalMiles());
    }

    public void addRecord(MaintenanceRecord record) {
        serviceHistory.add(record);
    }

    public List<MaintenanceRecord> getRecordsByType(MaintenanceType type) {
        List<MaintenanceRecord> results = new ArrayList<>();
        for(MaintenanceRecord r : serviceHistory) {
            if (r.getType() == type) {
                results.add(r);
            }
        }
        return results;
    }
    
    public List<MaintenanceRecord> getAllRecords() {
        return serviceHistory;
    }

    //Finds the most recent record of a given type, or null if none exists
    private MaintenanceRecord getLastRecord(MaintenanceType type) {
        MaintenanceRecord latest = null;
        for (MaintenanceRecord r : getRecordsByType(type)) {
            if (latest == null || r.getMileageAtService() > latest.getMileageAtService()) {
            latest = r;
            }
        }
        return latest;
    }

    // checks every maintanence type and returns which ones are overdue
    public List<String> getOverdueItems() {
        List<String> overdue = new ArrayList<>();
        for (MaintenanceType type : MaintenanceType.values()) {
            MaintenanceRecord last = getLastRecord(type);
            int interval = getIntervalFor(type);

            if (last == null) {
                overdue.add(type + ": never logged");
                continue;
            }

            int milesSinceService = currentMileage - last.getMileageAtService();
            if (milesSinceService >= interval) {
                overdue.add(String.format("%s: %d mi overdue (last done at %d mi, interval %d mi)",
                type, milesSinceService - interval, last.getMileageAtService(), interval));
            }
        }
        return overdue;
    }

    public int getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " - " + currentMileage + " mi";
    }
}