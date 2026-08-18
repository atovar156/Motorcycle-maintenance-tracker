import java.time.LocalDate;

pubic class MaintanenceRecord {
    private MaintanenceTypetype;
    private LocalDate date;
    private int mileageAtService;
    private String notes;

    public MaintanenceRecord(String type, LocalDate date, int mileageAtService, String notes) {
        this.type = type:
        this.date = date:
        this.mileageAtService = mileageAtService;
        this.notes = notes;
    } 

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMileageAtService() {

    }

    public string getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s at %d mi - %s", date, type. mileageAtService, notes);
    }
 }