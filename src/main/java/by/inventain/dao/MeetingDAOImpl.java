package by.inventain.dao;

import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public class MeetingDAOImpl implements MeetingDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    MeetingRepository meetingRepository;

    @Override
    @Transactional
    public int insert(Meeting meeting) {
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

    @Override
    public void update(Meeting meeting) {

    }

    @Override
    public void delete(Meeting meeting) {

    }

    @Override
    @Transactional
    public Meeting getById(int id) {
        Meeting meeting = meetingRepository.findOne(id);
        return meeting;
    }

    @Override
    public List<Meeting> getAll() {
        return null;
    }

    @Override
    @Transactional
    public List<Meeting> getAllByCompany(Company company) {
        List<Meeting> list = meetingRepository.findAllByCompany(company);
        return list;
    }
}
