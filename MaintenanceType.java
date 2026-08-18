public enum MaintanenceType {
    OIL_CHANGE(3000),
    CHAIN_LUBE(500),
    TIRE_CHECK(1000),
    BRAKE_PAD(6000),
    COOLANT_FLUSH(12000);

    private final int defaultIntervalMiles;

    MaintanenceType(int defaultIntervalMiles) {
        this.defaultIntervalMiles = defaultIntervalMiles;
    }

    public int getDefaultIntervalMiles() {
        return defaultIntervalMiles;
    }
}