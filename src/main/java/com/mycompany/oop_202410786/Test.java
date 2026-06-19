/*package com.mycompany.oop_202410786;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        ImpoundService service = new ImpoundService();

        int choice;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1) Add Owner");
            System.out.println("2) Add Vehicle");
            System.out.println("3) Add Employee");
            System.out.println("4) Add Lot");
            System.out.println("5) Create Record");
            System.out.println("6) Pay");
            System.out.println("7) Display Records");
            System.out.println("8) Close Record");
            System.out.println("9) Search Vehicle");
            System.out.println("0) Exit");
            choice = readInt(in, "Choose: ");

            switch (choice) {

                case 1 -> {
                    String oid = readNonEmpty(in, "Owner ID: ");
                    String fn = readNonEmpty(in, "First name: ");
                    String ln = readNonEmpty(in, "Last name: ");
                    String addr = readNonEmpty(in, "Address: ");
                    int age = readPositiveInt(in, "Age: ");

                    try {
                        service.addOwner(oid, fn, ln, addr, age);
                        System.out.println("Owner saved ✅");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 2 -> {
                    String plate = readNonEmpty(in, "Plate: ");
                    String color = readNonEmpty(in, "Color: ");
                    String model = readNonEmpty(in, "Model: ");
                    String type = readNonEmpty(in, "Type: ");

                    try {
                        System.out.print("Link this vehicle to an owner? (y/n): ");
                        String answer = in.nextLine().trim();

                        if (answer.equalsIgnoreCase("y")) {
                            String ownerId = readNonEmpty(in, "Owner ID: ");
                            service.addVehicleWithOwner(plate, color, model, type, ownerId);
                            System.out.println("Vehicle saved with owner ✅");
                        } else {
                            service.addVehicle(plate, color, model, type);
                            System.out.println("Vehicle saved without owner ✅");
                        }

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 3 -> {
                    String id = readNonEmpty(in, "Employee ID: ");
                    String name = readNonEmpty(in, "Name: ");
                    String office = readNonEmpty(in, "Office: ");
                    double salary = readNonNegativeDouble(in, "Salary: ");

                    try {
                        service.addEmployee(id, name, office, salary);
                        System.out.println("Employee saved ✅");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 4 -> {
                    String id = readNonEmpty(in, "Lot ID: ");
                    String loc = readNonEmpty(in, "Location: ");
                    int cap = readPositiveInt(in, "Capacity: ");

                    try {
                        service.addLot(id, loc, cap);
                        System.out.println("Lot saved ✅");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 5 -> {
                    System.out.println("1) Violation  2) Court");
                    int t = readInt(in, "Type: ");

                    try {
                        String recordId = readNonEmpty(in, "Record ID: ");
                        double fine = readNonNegativeDouble(in, "Fine: ");
                        String plate = readNonEmpty(in, "Vehicle Plate: ");
                        String employeeId = readNonEmpty(in, "Employee ID: ");
                        String lotId = readNonEmpty(in, "Lot ID: ");

                        if (t == 1) {
                            String type = readNonEmpty(in, "Violation type: ");
                            double rate = readNonNegativeDouble(in, "Daily rate: ");

                            service.createViolationRecord(recordId, fine, type, rate, plate, employeeId, lotId);
                            System.out.println("Violation record saved ✅");

                        } else if (t == 2) {
                            String caseNum = readNonEmpty(in, "Case number: ");
                            boolean cleared = readBoolean(in, "Court cleared (true/false): ");

                            service.createCourtRecord(recordId, fine, caseNum, cleared, plate, employeeId, lotId);
                            System.out.println("Court record saved ✅");

                        } else {
                            System.out.println("Invalid type.");
                        }

                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 6 -> {
                    String id = readNonEmpty(in, "Record ID: ");
                    double amount = readNonNegativeDouble(in, "Amount: ");

                    try {
                        service.pay(id, amount);
                        System.out.println("Payment done ✅");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 7 -> {
                    try {
                        service.displayAll();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 8 -> {
                    String id = readNonEmpty(in, "Record ID: ");

                    try {
                        service.closeRecord(id);
                        System.out.println("Record closed ✅");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 9 -> {
                    String plate = readNonEmpty(in, "Enter plate: ");

                    try {
                        service.searchVehicle(plate);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 0 -> System.out.println("Bye 👋");

                default -> System.out.println("Invalid choice");
            }

        } while (choice != 0);

        in.close();
    }

    private static String readNonEmpty(Scanner in, String msg) {
        while (true) {
            System.out.print(msg);
            String value = in.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static int readInt(Scanner in, String msg) {
        while (true) {
            System.out.print(msg);
            String value = in.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(Scanner in, String msg) {
        while (true) {
            int value = readInt(in, msg);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be greater than 0.");
        }
    }

    private static double readNonNegativeDouble(Scanner in, String msg) {
        while (true) {
            System.out.print(msg);
            String value = in.nextLine().trim();
            try {
                double number = Double.parseDouble(value);
                if (number >= 0) {
                    return number;
                }
                System.out.println("Value must be 0 or greater.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static boolean readBoolean(Scanner in, String msg) {
        while (true) {
            System.out.print(msg);
            String value = in.nextLine().trim().toLowerCase();

            if (value.equals("true") || value.equals("yes") || value.equals("y")) {
                return true;
            }
            if (value.equals("false") || value.equals("no") || value.equals("n")) {
                return false;
            }

            System.out.println("Please enter true/false or yes/no.");
        }
    }
}

 */