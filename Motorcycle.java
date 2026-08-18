import java.util.ArrayList;
import java.util.EnumMap;
import java.util.list;
import java.util.Map;

public class Motorcycle {
    private String make;
    private String model;
    private int year;
    private int currentMileage;
    private List<MaintanenceRecord> serviceHistory;
    private Map<MaintanenceType, Integer> customIntervals;

    public Motorcycle(String make, String model, int year, int currentMileage) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.currentMileage = currentMileage;
        this.serviceHistory = new ArrayList<>();
        this.customIntervals = new EnumMap<>(MaintanenceType.class);

    }

    // Let a bike override the default interval for a given type
    public void setCustomInterval(MaintanenceType type, int miles) {
        customIntervals.put(type, miles);
    }

    private int getIntervalfor(MaintanenceType type) {
        return customIntervals.getOrDefault(type, type.getDefaultIntervalMiles());
    }

    public void addRecord(MaintanenceRecord record) {
        serviceHistory.add(record);
    }

    public List<MaintanenceRecord> getRecordsByType(String type) {
        List<MaintanenceRecord> results = new ArrayList<>();
        for(MaintanenceRecord r : serviceHistory) {
            if(r.getType().equalsIgnoreCase(type)) {
                results.add(r);
            }
        }
        return results;
    }
    
    public List<MaintanenceRecord> getAllRecords() {
        return serviceHistory;
    }

    //Finds the most recent record of a given type, or null if none exists
    private MaintanenceRecord getLastRecord(MaintanenceType type) {
        MaintanenceRecord latest = null;
        for (MaintanenceRecord r : getRecordsByType(type)) {
            if (latest == null || r.getMileageAtService() > latest.getMileageAtService)
            latest = r;
        }
    }
    return latest;

    public int getCurrentMileage() {
        return currentMileage;
    }

    public void setCurrentMileage(int currentMileage) {
        this.currentMileage = currentMileage;
    }

    @Override
    public String toString() {
        return year + " " + make + " " + model + " - " + currentMileage + " mi";
    }
}