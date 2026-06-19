package com.mycompany.oop_202410786;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    private final ImpoundService service;

    public ApiController(ImpoundService service) {
        this.service = service;
    }

    record LoginRequest(String username, String password) {}
    record OwnerRequest(String ownerId, String firstName, String lastName, String address, int age) {}
    record EmployeeRequest(String employeeId, String name, String officeNumber, double salary) {}
    record PoliceRequest(String policeId, String firstName, String lastName) {}
    record LotRequest(String lotId, String location, int capacity) {}
    record ViolationOnlyRequest(String recordId, double fine, String violationType, String plateNumber, String policeId) {}
    record ViolationImpoundRequest(String recordId, double fine, String violationType, double dailyRate,
                                   String plateNumber, String policeId, String lotId) {}
    record PayRequest(String recordId, double amount) {}
    record CloseRequest(String recordId) {}

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {
        if ("admin".equals(req.username()) && "123".equals(req.password())) {
            return loginOk("ADMIN", "Hamza Allam", null);
        }
        if ("employee".equals(req.username()) && "123".equals(req.password())) {
            return loginOk("EMPLOYEE", "Ahmad Khaled", null);
        }
        if ("police".equals(req.username()) && "123".equals(req.password())) {
            return loginOk("POLICE", "Omar Nasser", null);
        }
        if ("viewer".equals(req.username()) && "123".equals(req.password())) {
            return loginOk("VIEWER", "Ali Mahmoud", "O1");
        }
        return error("Invalid username or password");
    }

    @PostMapping("/owners")
    public Map<String, Object> addOwner(@RequestBody OwnerRequest req) {
        try {
            service.addOwner(req.ownerId(), req.firstName(), req.lastName(), req.address(), req.age());
            return ok("Owner saved");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/vehicles")
    public Map<String, Object> addVehicle(
            @RequestParam String plateNumber,
            @RequestParam String color,
            @RequestParam String model,
            @RequestParam String vehicleType,
            @RequestParam(required = false) String ownerId,
            @RequestParam boolean isLicensed,
            @RequestParam MultipartFile image1,
            @RequestParam MultipartFile image2,
            @RequestParam MultipartFile image3
    ) {
        try {
            String img1 = saveFile(image1);
            String img2 = saveFile(image2);
            String img3 = saveFile(image3);

            if (ownerId != null && !ownerId.isBlank()) {
                service.addVehicleWithOwner(
                        plateNumber, color, model, vehicleType,
                        ownerId, isLicensed, img1, img2, img3
                );
            } else {
                service.addVehicle(
                        plateNumber, color, model, vehicleType,
                        isLicensed, img1, img2, img3
                );
            }

            return ok("Vehicle saved with uploaded images");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/employees")
    public Map<String, Object> addEmployee(@RequestBody EmployeeRequest req) {
        try {
            service.addEmployee(req.employeeId(), req.name(), req.officeNumber(), req.salary());
            return ok("Employee saved");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/police")
    public Map<String, Object> addPolice(@RequestBody PoliceRequest req) {
        try {
            service.addPolice(req.policeId(), req.firstName(), req.lastName());
            return ok("Police officer saved");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/lots")
    public Map<String, Object> addLot(@RequestBody LotRequest req) {
        try {
            service.addLot(req.lotId(), req.location(), req.capacity());
            return ok("Lot saved");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/records/police/violation-only")
    public Map<String, Object> violationOnly(@RequestBody ViolationOnlyRequest req) {
        try {
            service.createViolationOnly(
                    req.recordId(),
                    req.fine(),
                    req.violationType(),
                    req.plateNumber(),
                    req.policeId()
            );
            return ok("Violation only created");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/records/police/violation-impound")
    public Map<String, Object> violationImpound(@RequestBody ViolationImpoundRequest req) {
        try {
            service.createViolationImpound(
                    req.recordId(),
                    req.fine(),
                    req.violationType(),
                    req.dailyRate(),
                    req.plateNumber(),
                    req.policeId(),
                    req.lotId()
            );
            return ok("Violation + impound created");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/records/pay")
    public Map<String, Object> pay(@RequestBody PayRequest req) {
        try {
            service.pay(req.recordId(), req.amount());
            return ok("Payment saved");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @PostMapping("/records/close")
    public Map<String, Object> close(@RequestBody CloseRequest req) {
        try {
            service.closeRecord(req.recordId());
            return ok("Record closed");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @GetMapping("/vehicles/{plate}")
    public Map<String, Object> searchVehicle(@PathVariable String plate) {
        try {
            Map<String, Object> v = service.searchVehicleData(plate);
            if (v == null) return error("Vehicle not found");
            v.put("success", true);
            return v;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @GetMapping("/police/check/{plate}")
    public Map<String, Object> policeCheck(@PathVariable String plate) {
        try {
            Map<String, Object> result = service.policeCheck(plate);
            if (result == null) return error("Vehicle not found");
            result.put("success", true);
            return result;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @GetMapping("/viewer/plate/{plate}")
    public Map<String, Object> viewerByPlate(@PathVariable String plate) {
        try {
            Map<String, Object> result = service.policeCheck(plate);
            if (result == null) return error("Vehicle not found");
            result.put("success", true);
            return result;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }



    @GetMapping("/records")
    public Map<String, Object> allRecords() {
        try {
            List<Map<String, Object>> records = service.getAllRecords();
            Map<String, Object> res = new HashMap<>();
            res.put("success", true);
            res.put("records", records);
            return res;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    private String saveFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) return null;

        // مسار ثابت داخل المشروع
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(uploadDir + fileName);

        file.transferTo(dest);

        return fileName;
    }

    private Map<String, Object> ok(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("message", message);
        return map;
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        map.put("message", message);
        return map;
    }

    private Map<String, Object> loginOk(String role, String fullName, String ownerId) {
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("role", role);
        map.put("fullName", fullName);
        map.put("ownerId", ownerId);
        return map;
    }

    @GetMapping("/viewer/dashboard/{ownerId}")
    public Map<String, Object> getViewerDashboard(@PathVariable String ownerId) {
        try {
            Map<String, Object> data = service.getViewerDashboard(ownerId);
            if (data == null) return error("No data found for this ID");
            data.put("success", true);
            return data;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    @GetMapping("/search/plate/{plateNumber}")
    public Map<String, Object> searchByPlate(@PathVariable String plateNumber) {
        try {
            // نستخدم الدالة التي تبحث عن السيارة والمالك معاً
            Map<String, Object> result = service.policeCheck(plateNumber);

            if (result == null) {
                return error("عذراً، لا توجد سيارة مسجلة بهذا الرقم");
            }

            result.put("success", true);
            return result;
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}