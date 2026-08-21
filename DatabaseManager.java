import java.sql.*;
import java.time.LocalDate;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:garage.db";

    public DatabaseManager() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Class not found: " + e.getMessage());
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS motorcycles (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "make TEXT, model TEXT, year INTEGER, currentMileage INTEGER)");

            stmt.execute("CREATE TABLE IF NOT EXISTS maintenance_records (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "motorcycleId INTEGER," +
                    "type TEXT, date TEXT, mileageAtService INTEGER, notes TEXT," +
                    "FOREIGN KEY(motorcycleId) REFERENCES motorcycles(id))");
 
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    public int saveMotorcycle(Motorcycle m) {
        String sql = "INSERT INTO motorcycles (make, model, year, currentMileage) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, m.getMake());
            ps.setString(2, m.getModel());
            ps.setInt(3, m.getYear());
            ps.setInt(4, m.getCurrentMileage());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error saving motorcycle: " + e.getMessage());
        }
        return -1;
    }

    public void saveRecord(int motorcycleId, MaintenanceRecord r) {
        String sql = "INSERT INTO maintenance_records (motorcycleId, type, date, mileageAtService, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, motorcycleId);
                ps.setString(2, r.getType().name());
                ps.setString(3, r.getDate().toString());
                ps.setInt(4, r.getMileageAtService());
                ps.setString(5, r.getNotes());
                ps.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Error saving maintenance record: " + e.getMessage());
        }
    }

    public Garage loadGarage() {
        Garage garage = new Garage();
        
        String sql = "SELECT * FROM motorcycles";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Motorcycle m = new Motorcycle(
                        rs.getString("make"),
                        rs.getString("model"),
                        rs.getInt("year"),
                        rs.getInt("currentMileage")
                );
                m.setId(id);
                loadRecordsfor(m, conn);
                garage.addMotorcycle(m);
            }
    } catch (SQLException e) {
            System.out.println("Error loading garage: " + e.getMessage());
        }
        return garage;
    }

    private void loadRecordsfor(Motorcycle m, Connection conn) throws SQLException {
        String sql = "SELECT * FROM maintenance_records WHERE motorcycleId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MaintenanceType type = MaintenanceType.valueOf(rs.getString("type"));
                LocalDate date = LocalDate.parse(rs.getString("date"));
                int mileage = rs.getInt("mileageAtService");
                String notes = rs.getString("notes");
                m.addRecord(new MaintenanceRecord(type, date, mileage, notes));
            }
        }
    }

    public void updateMileage(Motorcycle m) {
        String sql = "UPDATE motorcycles SET currentMileage = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getCurrentMileage());
            ps.setInt(2, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating mileage: " + e.getMessage());
        }
    }
}