package by.inventain.controller;

import by.inventain.dao.CompanyDAO;
import by.inventain.dao.MeetingDAO;
import by.inventain.model.Company;
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
    @GetMapping("/companies/{id}/meetings")
    public Company getCompany(@PathVariable int id, @RequestParam("startDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm")  LocalDateTime startDate, @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm")LocalDateTime endDate){
        if (startDate==null||endDate==null)return companyDAO.getById(id);
        Company company = companyDAO.getById(id);
        if (company.getMeetings()==null) return null;
        for (int i=0; i<company.getMeetings().size();i++){
            LocalDateTime tmpStart = company.getMeetings().get(i).getStartTime();
            LocalDateTime tmpEnd = tmpStart.plusHours(company.getMeetings().get(i).getDuration());
            if (startDate.compareTo(tmpStart)==1||endDate.compareTo(tmpEnd)==-1){
                company.getMeetings().remove(i--);
            }
        }
        return company;
    }
    @GetMapping("/companies/{id}")
    public Company getCompanyById(@PathVariable int id){
        Company company = companyDAO.getById(id);
        company.getMeetings().clear();
        return company;
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
