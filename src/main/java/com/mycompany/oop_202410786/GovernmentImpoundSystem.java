package com.mycompany.oop_202410786;

public class GovernmentImpoundSystem {

    private Employee[] employees = new Employee[50];
    private Person[] owners = new Person[50];
    private Vehicle[] vehicles = new Vehicle[100];
    private ImpoundLot[] lots = new ImpoundLot[20];
    private ImpoundRecord[] records = new ImpoundRecord[200];

    private int empCount, vehicleCount, lotCount;
  private int recordCount = 0;
    private int ownerCount = 0;

    public GovernmentImpoundSystem() {}

    public Vehicle findVehicleByPlate(String plate) {
        for (int i = 0; i < vehicleCount; i++) {
            if (vehicles[i].getPlateNumber().equals(plate))
                return vehicles[i];
        }
        return null;
    }

    public void displayVehicleWithOwner(String plate) {
        Vehicle v = findVehicleByPlate(plate);
        if (v != null && v.getOwner() != null) {
            System.out.println("Vehicle: " + v.getPlateNumber());
            System.out.println("Owner: " + v.getOwner().getfName());
        }
    }

    public void displayAllRecords() {
        for (int i = 0; i < recordCount; i++) {
            System.out.println(
                records[i].getRecordId() + " - Fees: " +
                records[i].calculateTotalFees()
            );
            
        }
    }
    
    
    
    //  (: Helper Methods here 
    
    public boolean addOwner(Person p) {
    if (p == null) return false;

    if (ownerCount >= owners.length)
        return false;

    owners[ownerCount++] = p;
    return true;
}

    public boolean addVehicle(Vehicle v) {
    if (v == null) return false;
    if (vehicleCount >= vehicles.length) return false;

    vehicles[vehicleCount++] = v;
    return true;
}

    public boolean addEmployee(Employee e) {
    if (e == null) return false;
    if (empCount >= employees.length) return false;

    employees[empCount++] = e;
    return true;
}

    public boolean addLot(ImpoundLot l) {
    if (l == null) return false;
    if (lotCount >= lots.length) return false;

    lots[lotCount++] = l;
    return true;
}

public boolean addRecord(ImpoundRecord r) {
    if (r == null)
        return false;

    if (recordCount >= records.length)
        return false;

    records[recordCount++] = r;
    return true;
}
public ImpoundRecord findRecordById(String recordId) {
    if (recordId == null) return null;

    for (int i = 0; i < recordCount; i++) {
        if (records[i].getRecordId().equals(recordId)) {
            return records[i];
        }
    }
    return null;
}


}
