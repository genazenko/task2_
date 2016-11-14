package com.inventain.controller;

import com.inventain.model.Company;
import com.inventain.model.Employee;
import com.inventain.model.Information;
import com.inventain.model.Meeting;
import com.inventain.service.CompanyService;
import com.inventain.service.EmployeeService;
import com.inventain.service.InformationService;
import com.inventain.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@RestController
public class Controller {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private InformationService informationService;

    @GetMapping(value = "/companies/{id}/meetings", params = {"startDate", "endDate"})
    public ResponseEntity getCompany(@PathVariable int id,
                                     @RequestParam("startDate")
                                     @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime startDate,
                                     @RequestParam("endDate")
                                     @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime endDate) {
        return ResponseEntity.ok(meetingService.getAllInTime(id, startDate, endDate));

    }

    @GetMapping("/companies/{id}/meetings")
    public Map<LocalDate, Collection<Meeting>> getCompany(@PathVariable int id) {
        return meetingService.getAllByCompanyId(id);
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyInfo(id);
    }

    @PostMapping("/companies")
    public ResponseEntity insertCompany(@Valid @RequestBody Company company) {
        int generatedId = companyService.insert(company);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedId);
    }

    @PostMapping("/companies/{id}/meetings")
    public ResponseEntity insertMeeting(@Valid @RequestBody Meeting meeting, @PathVariable int id) {
        int generatedId = meetingService.insert(meeting, id);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create meeting");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedId);
    }

    @PostMapping("/companies/{id}/employee")
    public ResponseEntity insertEmployee(@Valid @RequestBody Employee employee, @PathVariable int id) {
        int generatedId = employeeService.insert(employee, id);
        if (generatedId == -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't create employee");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedId);
    }

    @PostMapping("/companies/{id}/meetings/list")
    public ResponseEntity insertListOfMeetings(@Valid @RequestBody List<Meeting> list, @PathVariable int id) {
        Map<String, List<Meeting>> result = meetingService.insertMeetings(id, list);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/information")
    public List<Information> getAllRows() {
        return informationService.getAllInf();
    }
}
