package by.inventain.controller;

import by.inventain.model.Company;
import by.inventain.model.Employee;
import by.inventain.model.Meeting;
import by.inventain.service.CompanyService;
import by.inventain.service.EmployeeService;
import by.inventain.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
public class Controller {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/companies/{id}/meetings")
    public Company getCompany(@PathVariable int id, @RequestParam("startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime endDate) {
        if (startDate == null || endDate == null) return companyService.getById(id);
        else {
            return companyService.getInTime(id, startDate, endDate);
        }
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyInfo(id);
    }

    @PostMapping("/companies")
    public int insertCompany(@RequestBody Company company) {
        return companyService.insert(company);
    }

    @PostMapping("/companies/{id}/meetings")
    public int insertMeeting(@RequestBody Meeting meeting, @PathVariable int id) {
        meeting.setCompany(companyService.getById(id));
        return meetingService.insert(meeting);
    }

    @PostMapping("/companies/{id}/employee")
    public int insertEmployee(@RequestBody Employee employee, @PathVariable int id) {
        employee.setCompany(companyService.getById(id));
        return employeeService.insert(employee);
    }
}
