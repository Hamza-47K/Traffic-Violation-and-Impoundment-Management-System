package com.mycompany.oop_202410786;


public class ViolationImpound extends ImpoundRecord{
    
    private String violationType;
    private double dailyRate;

    public ViolationImpound() {
    }

    public ViolationImpound(String violationType, double dailyRate) {
        this.violationType = violationType;
        this.dailyRate = dailyRate;
    }

    public String getViolationType() {
        return violationType;
    }

    public void setViolationType(String violationType) {
        this.violationType = violationType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }
    
       @Override
    public double calculateTotalFees() {
        return getFine() + (getImpoundDays() * dailyRate);
    }

    @Override
    public boolean canRelease() {
        return true;
    }
    
}
