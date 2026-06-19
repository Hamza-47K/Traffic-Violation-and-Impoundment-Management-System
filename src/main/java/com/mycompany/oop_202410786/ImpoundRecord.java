package com.mycompany.oop_202410786;

public abstract class ImpoundRecord {

    private String recordId;
    private double fine;
    private int impoundDays;
    private String reason;

    private double paidAmount;
    private boolean closed;

    private Vehicle vehicle;
    private Employee employee;
    private ImpoundLot lot;

    public ImpoundRecord() {}

    public abstract double calculateTotalFees();
    public abstract boolean canRelease();

    
    public void pay(double amount) {
        if (amount > 0) paidAmount += amount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    
    public void closeRecord() {
        closed = true;
    }

    public boolean isClosed() { return closed; }

   
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }

    public int getImpoundDays() { return impoundDays; }
    public void setImpoundDays(int impoundDays) { this.impoundDays = impoundDays; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public ImpoundLot getLot() { return lot; }
    public void setLot(ImpoundLot lot) { this.lot = lot; }
}
