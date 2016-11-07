package by.inventain.dao;

import by.inventain.model.Meeting;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by 123 on 07.11.2016.
 */
public class MeetingDAOImpl implements MeetingDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Override
    public int insert(Meeting meeting) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if (meeting.getCompany()!=null&&isValid(meeting)){
            session.persist(meeting);
            session.getTransaction().commit();
            session.flush();
            session.close();
            return meeting.getId();
        }
        else{
            session.close();
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
    public Meeting getById(int id) {
        Session session = sessionFactory.openSession();
        Meeting meeting =session.get(Meeting.class,id);
        return meeting;
    }

    @Override
    public List<Meeting> getAll() {
        return null;
    }

    private boolean isValid(Meeting meeting) {
        if(meeting.getStartTime().toLocalTime().compareTo(meeting.getCompany().getOpenTime())==-1) return false;
        if (meeting.getStartTime().plusHours(meeting.getDuration()).toLocalTime().compareTo(meeting.getCompany().getCloseTime())==1) return false;
        for (int i=0; i<meeting.getCompany().getMeetings().size();i++){
            LocalDateTime startTime = meeting.getCompany().getMeetings().get(i).getStartTime();
            LocalDateTime endTime = startTime.plusHours(meeting.getCompany().getMeetings().get(i).getDuration());
            if (meeting.getStartTime().compareTo(startTime)==0||meeting.getStartTime().compareTo(startTime)==1&&meeting.getStartTime().compareTo(endTime)==-1) return false;
            if (meeting.getStartTime().plusHours(meeting.getDuration()).compareTo(endTime)==0||meeting.getStartTime().plusHours(meeting.getDuration()).compareTo(endTime)==-1&&meeting.getStartTime().plusHours(meeting.getDuration()).compareTo(startTime)==1) return false;
        }
        return true;
    }
}
