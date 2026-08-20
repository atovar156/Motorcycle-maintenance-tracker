import java.time.LocalDate;

public class MaintenanceRecord {
    private MaintenanceType type;
    private LocalDate date;
    private int mileageAtService;
    private String notes;

    public MaintenanceRecord(MaintenanceType type, LocalDate date, int mileageAtService, String notes) {
        this.type = type;
        this.date = date;
        this.mileageAtService = mileageAtService;
        this.notes = notes;
    } 

    public MaintenanceType getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMileageAtService() {
        return mileageAtService;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s at %d mi - %s", date, type, mileageAtService, notes);
    }
 }