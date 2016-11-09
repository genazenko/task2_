package by.inventain.service;

import by.inventain.dao.MeetingRepository;
import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    @Transactional
    public int insert(Meeting meeting) {
        if (meeting.getCompany() == null || meeting.getSubmittedBy() == null) return -1;
        LocalDateTime startTime = meeting.getStartTime();
        LocalDateTime endTime = meeting.getEndTime();
        boolean checkInCompanyTime = meetingRepository.checkCompanyTime(startTime.toLocalTime(), endTime.toLocalTime(), meeting.getCompany().getId());
        boolean checkOverlap = meetingRepository.checkOverlap(startTime, endTime, meeting.getCompany());
        if (meeting.getCompany() != null && checkInCompanyTime && checkOverlap) {
            meetingRepository.save(meeting);
            return meeting.getId();
        }
        return -1;
    }

    @Transactional
    public Meeting getById(int id) {
        Meeting meeting = meetingRepository.findOne(id);
        return meeting;
    }

    @Transactional
    public List<Meeting> getAllByCompany(Company company) {
        List<Meeting> list = meetingRepository.findAllByCompany(company);
        return list;
    }
}
