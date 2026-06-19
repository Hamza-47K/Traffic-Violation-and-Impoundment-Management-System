package com.mycompany.oop_202410786;


public class ImpoundLot {
    
    private String lotId;
    private String location;
    private int capacity;

    public ImpoundLot() {
    }

    public ImpoundLot(String lotId, String location, int capacity) {
        this.lotId = lotId;
        this.location = location;
        this.capacity = capacity;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
}
