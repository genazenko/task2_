package by.inventain.controller;

import by.inventain.dao.CompanyDAO;
import by.inventain.dao.EmployeeDAO;
import by.inventain.dao.MeetingDAO;
import by.inventain.model.Company;
import by.inventain.model.Employee;
import by.inventain.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
public class Controller {
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private MeetingDAO meetingDAO;
    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping("/companies/{id}/meetings")
    public Company getCompany(@PathVariable int id, @RequestParam("startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm") LocalDateTime endDate) {
        if (startDate == null || endDate == null) return companyDAO.getById(id);
        else {
            return companyDAO.getInTime(id, startDate, endDate);
        }
    }

    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id) {
        Company company = companyDAO.getById(id);
        company.getMeetings().clear();
        return company;
    }

    @PostMapping("/companies")
    public int insertCompany(@RequestBody Company company) {
        return companyDAO.insert(company);
    }

    @PostMapping("/companies/{id}/meetings")
    public int insertMeeting(@RequestBody Meeting meeting, @PathVariable int id) {
        meeting.setCompany(companyDAO.getById(id));
        return meetingDAO.insert(meeting);
    }

    @PostMapping("/companies/{id}/employee")
    public int insertEmployee(@RequestBody Employee employee, @PathVariable int id) {
        employee.setCompany(companyDAO.getById(id));
        return employeeDAO.insert(employee);
    }
}
