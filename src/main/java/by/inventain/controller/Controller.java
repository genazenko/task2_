package by.inventain.controller;

import by.inventain.dao.CompanyDAO;
import by.inventain.dao.MeetingDAO;
import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 123 on 06.11.2016.
 */
@RestController
public class Controller {
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private MeetingDAO meetingDAO;
    @GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable int id){
        return companyDAO.getById(id);
    }
    @PostMapping("/companies")
    public int insertCompany(@RequestBody Company company){
        return companyDAO.insert(company);
    }
    @PostMapping("/companies/{id}/meetings")
    public int insertMeeting(@RequestBody Meeting meeting,@PathVariable int id){
        meeting.setCompany(companyDAO.getById(id));
        return meetingDAO.insert(meeting);
    }
}
