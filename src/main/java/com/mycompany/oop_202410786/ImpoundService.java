package com.mycompany.oop_202410786;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ImpoundService {

    private final DatabaseManager db;

    public ImpoundService(DatabaseManager db) {
        this.db = db;
    }

    public void addOwner(String id, String fn, String ln, String address, int age) {
        db.savePerson(new Person(id, fn, ln, address, age));
    }

    public void addVehicle(String plate, String color, String model, String type,
                           boolean isLicensed, String image1, String image2, String image3) {
        db.saveVehicle(new Vehicle(plate, color, model, type), isLicensed, image1, image2, image3);
    }

    public void addVehicleWithOwner(String plate, String color, String model, String type,
                                    String ownerId, boolean isLicensed,
                                    String image1, String image2, String image3) {
        Vehicle v = new Vehicle(plate, color, model, type);
        Person owner = db.findPersonById(ownerId);
        if (owner == null) throw new RuntimeException("Owner not found with ID: " + ownerId);
        v.setOwner(owner);
        db.saveVehicle(v, isLicensed, image1, image2, image3);
    }

    public void addEmployee(String id, String name, String office, double salary) {
        db.saveEmployee(new Employee(id, name, office, salary));
    }

    public void addPolice(String policeId, String firstName, String lastName) {
        db.savePolice(policeId, firstName, lastName);
    }

    public void addLot(String id, String location, int capacity) {
        db.saveLot(new ImpoundLot(id, location, capacity));
    }

    public void createViolationOnly(String recordId, double fine, String violationType,
                                    String plateNumber, String policeId) {
        Map<String, Object> vehicle = db.findVehicleWithOwnerAndLicense(plateNumber);
        if (vehicle == null) throw new RuntimeException("Vehicle not found");
        if (!db.policeExists(policeId)) throw new RuntimeException("Police not found");

        ViolationImpound r = new ViolationImpound(violationType, 0);
        r.setRecordId(recordId);
        r.setFine(fine);
        db.saveViolationOnlyRecord(r, plateNumber, policeId);
    }

    public void createViolationImpound(String recordId, double fine, String violationType, double dailyRate,
                                       String plateNumber, String policeId, String lotId) {
        Map<String, Object> vehicle = db.findVehicleWithOwnerAndLicense(plateNumber);
        if (vehicle == null) throw new RuntimeException("Vehicle not found");
        if (!db.policeExists(policeId)) throw new RuntimeException("Police not found");
        if (db.findLotById(lotId) == null) throw new RuntimeException("Lot not found");

        ViolationImpound r = new ViolationImpound(violationType, dailyRate);
        r.setRecordId(recordId);
        r.setFine(fine);
        db.saveViolationImpoundRecord(r, plateNumber, policeId, lotId);
    }

    public void pay(String recordId, double amount) {
        db.payRecord(recordId, amount);
    }

    public void closeRecord(String recordId) {
        db.closeRecord(recordId);
    }

    public List<Map<String, Object>> getAllRecords() {
        return db.getAllRecords();
    }

    public Map<String, Object> searchVehicleData(String plate) {
        return db.findVehicleWithOwnerAndLicense(plate);
    }

    public Map<String, Object> policeCheck(String plate) {
        return db.getPoliceVehicleStatus(plate);
    }

    public Map<String, Object> viewerDashboard(String ownerId) {
        return db.getViewerDashboard(ownerId);
    }
    public Map<String, Object> getViewerData(String ownerId) {
        return db.getViewerDashboard(ownerId);
    }
    public Map<String, Object> getViewerDashboard(String ownerId) {
        return db.getViewerDashboard(ownerId);
    }


}