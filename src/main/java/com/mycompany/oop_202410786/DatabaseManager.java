package com.mycompany.oop_202410786;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

@Component
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:impound_system.db";

    public DatabaseManager() {
        createTables();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void createTables() {
        try (Connection con = connect(); Statement stmt = con.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS persons (
                    owner_id TEXT PRIMARY KEY,
                    first_name TEXT,
                    last_name TEXT,
                    address TEXT,
                    age INTEGER
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS vehicles (
                    plate_number TEXT PRIMARY KEY,
                    color TEXT,
                    model TEXT,
                    vehicle_type TEXT,
                    owner_id TEXT,
                    is_licensed INTEGER,
                    image1 TEXT,
                    image2 TEXT,
                    image3 TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS employees (
                    employee_id TEXT PRIMARY KEY,
                    name TEXT,
                    office_number TEXT,
                    salary REAL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS police (
                    police_id TEXT PRIMARY KEY,
                    first_name TEXT,
                    last_name TEXT
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS impound_lots (
                    lot_id TEXT PRIMARY KEY,
                    location TEXT,
                    capacity INTEGER
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS impound_records (
                    record_id TEXT PRIMARY KEY,
                    record_type TEXT,
                    fine REAL,
                    paid_amount REAL,
                    closed INTEGER,
                    plate_number TEXT,
                    officer_id TEXT,
                    lot_id TEXT,
                    violation_type TEXT,
                    daily_rate REAL,
                    case_number TEXT,
                    is_court_cleared INTEGER
                )
            """);

        } catch (SQLException e) {
            throw new RuntimeException("DB init error: " + e.getMessage());
        }
    }

    public void savePerson(Person p) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO persons VALUES (?, ?, ?, ?, ?)")) {

            ps.setString(1, p.getOwnerId());
            ps.setString(2, p.getfName());
            ps.setString(3, p.getlName());
            ps.setString(4, p.getAddress());
            ps.setInt(5, p.getAge());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("savePerson error: " + e.getMessage());
        }
    }

    public Person findPersonById(String ownerId) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM persons WHERE owner_id = ?")) {

            ps.setString(1, ownerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Person(
                        rs.getString("owner_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("address"),
                        rs.getInt("age")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("findPersonById error: " + e.getMessage());
        }
    }

    public void saveVehicle(Vehicle v, boolean isLicensed, String image1, String image2, String image3) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO vehicles VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, v.getPlateNumber());
            ps.setString(2, v.getColor());
            ps.setString(3, v.getModel());
            ps.setString(4, v.getVehicleType());
            ps.setString(5, v.getOwner() != null ? v.getOwner().getOwnerId() : null);
            ps.setInt(6, isLicensed ? 1 : 0);
            ps.setString(7, image1);
            ps.setString(8, image2);
            ps.setString(9, image3);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("saveVehicle error: " + e.getMessage());
        }
    }

    public Map<String, Object> findVehicleWithOwnerAndLicense(String plate) {
        String sql = """
            SELECT v.plate_number, v.color, v.model, v.vehicle_type, v.is_licensed,
                   v.image1, v.image2, v.image3,
                   p.owner_id, p.first_name, p.last_name, p.address, p.age
            FROM vehicles v
            LEFT JOIN persons p ON v.owner_id = p.owner_id
            WHERE v.plate_number = ?
        """;

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            Map<String, Object> result = new HashMap<>();
            result.put("plateNumber", rs.getString("plate_number"));
            result.put("color", rs.getString("color"));
            result.put("model", rs.getString("model"));
            result.put("vehicleType", rs.getString("vehicle_type"));
            result.put("isLicensed", rs.getInt("is_licensed") == 1);
            result.put("image1", rs.getString("image1"));
            result.put("image2", rs.getString("image2"));
            result.put("image3", rs.getString("image3"));

            String ownerId = rs.getString("owner_id");
            if (ownerId != null) {
                Map<String, Object> owner = new HashMap<>();
                owner.put("ownerId", ownerId);
                owner.put("firstName", rs.getString("first_name"));
                owner.put("lastName", rs.getString("last_name"));
                owner.put("address", rs.getString("address"));
                owner.put("age", rs.getInt("age"));
                result.put("owner", owner);
            } else {
                result.put("owner", null);
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("findVehicleWithOwnerAndLicense error: " + e.getMessage());
        }
    }

    public void saveEmployee(Employee e) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO employees VALUES (?, ?, ?, ?)")) {

            ps.setString(1, e.getEmployeeId());
            ps.setString(2, e.getName());
            ps.setString(3, e.getOfficeNumber());
            ps.setDouble(4, e.getSalary());
            ps.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("saveEmployee error: " + ex.getMessage());
        }
    }

    public Employee findEmployeeById(String id) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM employees WHERE employee_id = ?")) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Employee(
                        rs.getString("employee_id"),
                        rs.getString("name"),
                        rs.getString("office_number"),
                        rs.getDouble("salary")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("findEmployeeById error: " + e.getMessage());
        }
    }

    public void savePolice(String policeId, String firstName, String lastName) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO police VALUES (?, ?, ?)")) {

            ps.setString(1, policeId);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("savePolice error: " + e.getMessage());
        }
    }

    public boolean policeExists(String policeId) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT 1 FROM police WHERE police_id = ?")) {

            ps.setString(1, policeId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new RuntimeException("policeExists error: " + e.getMessage());
        }
    }

    public void saveLot(ImpoundLot lot) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO impound_lots VALUES (?, ?, ?)")) {

            ps.setString(1, lot.getLotId());
            ps.setString(2, lot.getLocation());
            ps.setInt(3, lot.getCapacity());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("saveLot error: " + e.getMessage());
        }
    }

    public ImpoundLot findLotById(String id) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM impound_lots WHERE lot_id = ?")) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ImpoundLot(
                        rs.getString("lot_id"),
                        rs.getString("location"),
                        rs.getInt("capacity")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("findLotById error: " + e.getMessage());
        }
    }

    public void saveViolationOnlyRecord(ViolationImpound r, String plateNumber, String policeId) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO impound_records VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, r.getRecordId());
            ps.setString(2, "Violation");
            ps.setDouble(3, r.getFine());
            ps.setDouble(4, r.getPaidAmount());
            ps.setInt(5, r.isClosed() ? 1 : 0);
            ps.setString(6, plateNumber);
            ps.setString(7, policeId);
            ps.setNull(8, Types.VARCHAR);
            ps.setString(9, r.getViolationType());
            ps.setNull(10, Types.REAL);
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.INTEGER);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("saveViolationOnlyRecord error: " + e.getMessage());
        }
    }

    public void saveViolationImpoundRecord(ViolationImpound r, String plateNumber, String policeId, String lotId) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO impound_records VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, r.getRecordId());
            ps.setString(2, "Violation + Impound");
            ps.setDouble(3, r.getFine());
            ps.setDouble(4, r.getPaidAmount());
            ps.setInt(5, r.isClosed() ? 1 : 0);
            ps.setString(6, plateNumber);
            ps.setString(7, policeId);
            ps.setString(8, lotId);
            ps.setString(9, r.getViolationType());
            ps.setDouble(10, r.getDailyRate());
            ps.setNull(11, Types.VARCHAR);
            ps.setNull(12, Types.INTEGER);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("saveViolationImpoundRecord error: " + e.getMessage());
        }
    }

    public void payRecord(String id, double amount) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE impound_records SET paid_amount = paid_amount + ? WHERE record_id = ?")) {

            ps.setDouble(1, amount);
            ps.setString(2, id);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("No record found with ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("payRecord error: " + e.getMessage());
        }
    }

    public void closeRecord(String id) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE impound_records SET closed = 1 WHERE record_id = ?")) {

            ps.setString(1, id);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("No record found with ID: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("closeRecord error: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> getAllRecords() {
        List<Map<String, Object>> list = new ArrayList<>();

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM impound_records")) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("recordId", rs.getString("record_id"));
                row.put("recordType", rs.getString("record_type"));
                row.put("fine", rs.getDouble("fine"));
                row.put("paidAmount", rs.getDouble("paid_amount"));
                row.put("remainingAmount", rs.getDouble("fine") - rs.getDouble("paid_amount"));
                row.put("closed", rs.getInt("closed") == 1);
                row.put("plateNumber", rs.getString("plate_number"));
                row.put("officerId", rs.getString("officer_id"));
                row.put("lotId", rs.getString("lot_id"));
                row.put("violationType", rs.getString("violation_type"));
                row.put("dailyRate", rs.getObject("daily_rate"));
                list.add(row);
            }

        } catch (SQLException e) {
            throw new RuntimeException("getAllRecords error: " + e.getMessage());
        }

        return list;
    }

    public Map<String, Object> getPoliceVehicleStatus(String plate) {
        String sql = """
            SELECT
                v.plate_number,
                v.color,
                v.model,
                v.vehicle_type,
                v.is_licensed,
                v.image1,
                v.image2,
                v.image3,
                p.owner_id,
                p.first_name,
                p.last_name,
                p.address,
                p.age,
                EXISTS(
                    SELECT 1 FROM impound_records ir
                    WHERE ir.plate_number = v.plate_number
                      AND ir.record_type = 'Violation + Impound'
                      AND ir.closed = 0
                ) AS has_open_impound,
                EXISTS(
                    SELECT 1 FROM impound_records ir
                    WHERE ir.plate_number = v.plate_number
                ) AS has_violation
            FROM vehicles v
            LEFT JOIN persons p ON v.owner_id = p.owner_id
            WHERE v.plate_number = ?
        """;

        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            Map<String, Object> result = new HashMap<>();
            result.put("plateNumber", rs.getString("plate_number"));
            result.put("color", rs.getString("color"));
            result.put("model", rs.getString("model"));
            result.put("vehicleType", rs.getString("vehicle_type"));
            result.put("isLicensed", rs.getInt("is_licensed") == 1);
            result.put("image1", rs.getString("image1"));
            result.put("image2", rs.getString("image2"));
            result.put("image3", rs.getString("image3"));
            result.put("hasOpenImpound", rs.getInt("has_open_impound") == 1);
            result.put("hasViolation", rs.getInt("has_violation") == 1);

            String ownerId = rs.getString("owner_id");
            if (ownerId != null) {
                Map<String, Object> owner = new HashMap<>();
                owner.put("ownerId", ownerId);
                owner.put("firstName", rs.getString("first_name"));
                owner.put("lastName", rs.getString("last_name"));
                owner.put("address", rs.getString("address"));
                owner.put("age", rs.getInt("age"));
                result.put("owner", owner);
            } else {
                result.put("owner", null);
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException("getPoliceVehicleStatus error: " + e.getMessage());
        }
    }

    public Map<String, Object> getViewerDashboard(String plate) {
        String vehicleSql = """
            SELECT plate_number, color, model, vehicle_type, is_licensed, image1, image2, image3
            FROM vehicles
            WHERE owner_id = ?
            LIMIT 1
        """;

        String recordSql = """
            SELECT record_id, record_type, fine, paid_amount, closed, plate_number, violation_type, lot_id
            FROM impound_records
            WHERE plate_number IN (SELECT plate_number FROM vehicles WHERE owner_id = ?)
            ORDER BY record_id DESC
        """;

        try (Connection con = connect()) {
            Map<String, Object> result = new HashMap<>();

            try (PreparedStatement ps = con.prepareStatement(vehicleSql)) {
                ps.setString(1, plate);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Map<String, Object> vehicle = new HashMap<>();
                    vehicle.put("plateNumber", rs.getString("plate_number"));
                    vehicle.put("color", rs.getString("color"));
                    vehicle.put("model", rs.getString("model"));
                    vehicle.put("vehicleType", rs.getString("vehicle_type"));
                    vehicle.put("isLicensed", rs.getInt("is_licensed") == 1);
                    vehicle.put("image1", rs.getString("image1"));
                    vehicle.put("image2", rs.getString("image2"));
                    vehicle.put("image3", rs.getString("image3"));
                    result.put("vehicle", vehicle);
                } else {
                    result.put("vehicle", null);
                }
            }

            List<Map<String, Object>> violations = new ArrayList<>();
            try (PreparedStatement ps = con.prepareStatement(recordSql)) {
                ps.setString(1, plate);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    double fine = rs.getDouble("fine");
                    double paid = rs.getDouble("paid_amount");
                    row.put("recordId", rs.getString("record_id"));
                    row.put("recordType", rs.getString("record_type"));
                    row.put("violationType", rs.getString("violation_type"));
                    row.put("fine", fine);
                    row.put("paidAmount", paid);
                    row.put("remainingAmount", fine - paid);
                    row.put("closed", rs.getInt("closed") == 1);
                    row.put("lotId", rs.getString("lot_id"));
                    row.put("plateNumber", rs.getString("plate_number"));
                    violations.add(row);
                }
            }

            result.put("violations", violations);
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("getViewerDashboard error: " + e.getMessage());
        }
    }
}