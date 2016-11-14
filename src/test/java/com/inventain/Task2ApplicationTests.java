package com.inventain;

import com.inventain.dao.CompanyRepository;
import com.inventain.dao.EmployeeRepository;
import com.inventain.dao.MeetingRepository;
import com.inventain.model.Company;
import com.inventain.model.Employee;
import com.inventain.model.Meeting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class Task2ApplicationTests {

    private static final String VALIDCOMPANY = "{\"name\":\"BSU\",\"openTime\":\"09:00\",\"closeTime\":\"17:30\"}";
    private static final String INVALIDCOMPANY = "{\"openTime\":\"09:00\",\"closeTime\":\"17:30\",\"name\":\"BSU\"\"id\":\"1\"}";
    private static final String EMPLOYEE = "{\"firstName\":\"Asd\",\"lastName\":\"Qwe\"}";
    private static final String MEETING = "{\"submittedBy\":{\"empId\":\"1\"},\"startTime\":\"2014-03-25T15:00\",\"endTime\":\"2014-03-25T15:30\"}";
    private static final String expectedCompany = "{\"name\":\"inventain\",\"openTime\":\"10:00\",\"closeTime\":\"20:00\"}";
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MeetingRepository meetingRepository;
    private Company company;
    private Employee employee;
    private Meeting meeting;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        company = new Company();
        company.setName("Zxc");
        company.setOpenTime(LocalTime.of(9, 15));
        company.setCloseTime(LocalTime.of(20, 0));
        employee = new Employee();
        employee.setCompany(company);
        employee.setFirstName("Ook");
        employee.setLastName("Ffa");
        meeting = new Meeting();
        meeting.setSubmittedBy(employee);
        meeting.setCompany(company);
        meeting.setStartTime(LocalDateTime.of(2015, 5, 4, 8, 0));
        meeting.setEndTime(LocalDateTime.of(2015, 5, 4, 9, 0));
    }

    @Test
    public void insertCompanyTest() throws Exception {

        this.mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(INVALIDCOMPANY))
                .andExpect(status().is(400));

        this.mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(VALIDCOMPANY))
                .andExpect(status().isCreated());
    }

    @Test
    public void insertEmployeeTest() throws Exception {

        this.mockMvc.perform(post("/companies/1/employee").contentType(MediaType.APPLICATION_JSON).content(EMPLOYEE))
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/companies/-1/employee").contentType(MediaType.APPLICATION_JSON).content(EMPLOYEE))
                .andExpect(status().is(400));
    }

    @Test
    public void insertMeetingTest() throws Exception {

        this.mockMvc.perform(post("/companies/1/meetings").contentType(MediaType.APPLICATION_JSON).content(MEETING))
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/companies/-1/meetings").contentType(MediaType.APPLICATION_JSON).content(MEETING))
                .andExpect(status().is(400));
    }

    @Test
    public void getCompanyByIdTest() throws Exception {
        this.mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCompany));
    }

    @Test
    public void repositorySaveTest() throws Exception {
        companyRepository.save(company);
        assert (company.getId() != 0);
        employeeRepository.save(employee);
        assert (employee.getEmpId() != 0);
        meetingRepository.save(meeting);
        assert (meeting.getId() != 0);
    }

    @Test
    public void meetingRepositoryTest() throws Exception {
        boolean flag = meetingRepository.checkCompanyAndEmployee(1, 3);
        assert (!flag);
        flag = meetingRepository.checkCompanyAndEmployee(1, 1);
        assert (flag);
        flag = meetingRepository.checkCompanyTime("07:00", "08:00", 1);
        assert (!flag);
        flag = meetingRepository.checkCompanyTime("10:00", "11:00", 1);
        assert (flag);
        Company testCompany = companyRepository.findOne(1);
        flag = meetingRepository.checkOverlap(LocalDateTime.of(2011, 03, 22, 12, 20), LocalDateTime.of(2011, 03, 22, 12, 40), testCompany);
        assert (!flag);
        flag = meetingRepository.checkOverlap(LocalDateTime.of(2011, 03, 22, 12, 30), LocalDateTime.of(2011, 03, 22, 12, 40), testCompany);
        assert (flag);
        List<Meeting> meetings = meetingRepository.findAllByCompanyIdOrderByStartTime(3);
        assert (meetings.size() == 0);
        meetings = meetingRepository.findAllByCompanyIdOrderByStartTime(1);
        assert (meetings.size() > 0);
        meetings = meetingRepository.findAllInTime(1, LocalDateTime.of(2011, 03, 22, 12, 00), LocalDateTime.of(2011, 03, 22, 13, 00));
        assert (meetings.size() == 1);
    }

    @After
    public void tearDown() {

    }

}
