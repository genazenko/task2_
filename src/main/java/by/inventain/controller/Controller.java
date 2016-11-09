package by.inventain.controller;

import by.inventain.model.Company;
import by.inventain.model.Employee;
import by.inventain.model.Meeting;
import by.inventain.service.CompanyService;
import by.inventain.service.EmployeeService;
import by.inventain.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;


@RestController
public class Controller {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/companies/{id}/meetings")
    public Map<LocalDate, Collection<Meeting>> getCompany(@PathVariable int id,
                                                          @RequestParam("startDate")
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime startDate,
                                                          @RequestParam("endDate")
                                                          @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            return meetingService.getAllByCompanyId(id);
        } else {
            return meetingService.getAllInTime(id, startDate, endDate);
        }
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyInfo(id);
    }

    @PostMapping("/companies")
    public ResponseEntity insertCompany(@RequestBody Company company) {
        int generatedId = companyService.insert(company);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok(generatedId);
    }

    @PostMapping("/companies/{id}/meetings")
    public ResponseEntity insertMeeting(@RequestBody Meeting meeting, @PathVariable int id) {
        int generatedId = meetingService.insert(meeting, id);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create meeting");
        }
        return ResponseEntity.ok(generatedId);
    }

    @PostMapping("/companies/{id}/employee")
    public ResponseEntity insertEmployee(@RequestBody Employee employee, @PathVariable int id) {
        int generatedId = employeeService.insert(employee, id);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create employee");
        }
        return ResponseEntity.ok(generatedId);
    }
}
