
package com.mycompany.oop_202410786;


public class Vehicle {
    
    private String plateNumber;
    private String color;
    private String model;
    private String vehicleType;

    
     private Person owner;

        
    public Vehicle() {
    }

    public Vehicle(String plateNumber, String color, String model, String vehicleType) {
        this.plateNumber = plateNumber;
        this.color = color;
        this.model = model;
        this.vehicleType = vehicleType;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    
}
