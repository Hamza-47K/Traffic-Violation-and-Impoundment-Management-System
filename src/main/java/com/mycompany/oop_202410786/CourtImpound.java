package com.mycompany.oop_202410786;


public class CourtImpound extends ImpoundRecord {
    
     private String caseNumber;
    private boolean isCourtCleared;

    public CourtImpound() {
    }

    public CourtImpound(String caseNumber, boolean isCourtCleared) {
        this.caseNumber = caseNumber;
        this.isCourtCleared = isCourtCleared;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public boolean isIsCourtCleared() {
        return isCourtCleared;
    }

    public void setIsCourtCleared(boolean isCourtCleared) {
        this.isCourtCleared = isCourtCleared;
    }
    
      @Override
    public double calculateTotalFees() {
        return getFine();
    }

    @Override
    public boolean canRelease() {
        return isCourtCleared;
    }
}
