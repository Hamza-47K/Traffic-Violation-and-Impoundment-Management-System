
package com.mycompany.oop_202410786;


public class Person {
    
    private String ownerId;
    private String fName;
    private String lName;
    private String address;
    private int age;

    public Person() {
    }

    public Person(String ownerId, String fName, String lName, String address, int age) {
        this.ownerId = ownerId;
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.age = age;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    
}
