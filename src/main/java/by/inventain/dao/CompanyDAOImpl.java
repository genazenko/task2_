package by.inventain.dao;

import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by 123 on 06.11.2016.
 */
public class CompanyDAOImpl implements CompanyDAO {
    @Autowired
    SessionFactory sessionFactory;
    @Transactional
    @Override
    public int insert(Company company) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        checkCompanyTime(company);
        checkOverlap(company);
        if(company.getMeetings()!=null) {
            for (int i = 0; i < company.getMeetings().size(); i++) {
                company.getMeetings().get(i).setCompany(company);
            }
        }
        session.persist(company);
        session.getTransaction().commit();
        session.flush();
        session.close();
        return company.getId();
    }
    @Transactional
    @Override
    public void update(Company company) {
    }
    @Transactional
    @Override
    public void delete(Company company) {
    }
    @Transactional
    @Override
    public Company getById(int id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Meeting WHERE company = "+id);
        List<Meeting> list = query.list();
        Company company = session.get(Company.class,id);
        sortByMeetingTime(company);
        company.setMeetings(list);
        session.flush();
        session.close();
        return company;
    }
    @Transactional
    @Override
    public List<Company> getAll() {
        return null;
    }

    private void checkOverlap(Company company){
        if (company.getMeetings()==null)return;
        for (int i=0; i<company.getMeetings().size();i++){
            LocalDateTime startTime = company.getMeetings().get(i).getStartTime();
            LocalDateTime endTime = startTime.plusHours(company.getMeetings().get(i).getDuration());
            for (int j=i+1;j<company.getMeetings().size();j++){
                LocalDateTime tmpStart = company.getMeetings().get(j).getStartTime();
                LocalDateTime tmpEnd = tmpStart.plusHours(company.getMeetings().get(j).getDuration());
                if (startTime.compareTo(tmpStart)==0||startTime.compareTo(tmpStart)==-1&&endTime.compareTo(tmpStart)==1){
                    company.getMeetings().remove(j--);
                    continue;
                }
                if (endTime.compareTo(tmpEnd)==0||endTime.compareTo(tmpEnd)==1&&startTime.compareTo(tmpEnd)==-1){
                    company.getMeetings().remove(j--);
                    continue;
                }
            }
        }
    }

    private void checkCompanyTime(Company company){
        if (company.getMeetings()==null) return;
        for (int i=0; i<company.getMeetings().size();i++){
            LocalTime startTime = company.getMeetings().get(i).getStartTime().toLocalTime();
            LocalTime endTime = startTime.plusHours(company.getMeetings().get(i).getDuration());
            if (startTime.compareTo(company.getOpenTime())==-1||endTime.compareTo(company.getCloseTime())==1){
                company.getMeetings().remove(i--);
            }
        }
    }

    private void sortByMeetingTime(Company company){
        if (company.getMeetings()==null)return;
        company.getMeetings().sort((Meeting m1, Meeting m2)->m1.getStartTime().compareTo(m2.getStartTime()));
    }




}
