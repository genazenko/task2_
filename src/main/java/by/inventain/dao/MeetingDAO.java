package by.inventain.dao;

import by.inventain.model.Meeting;

import java.util.List;

/**
 * Created by 123 on 06.11.2016.
 */
public interface MeetingDAO {
    public int insert(Meeting meeting);
    public void update(Meeting meeting);
    public void delete(Meeting meeting);
    public Meeting getById(int id);
    public List<Meeting> getAll();
    public List<Meeting> getAllByCompany(int id);
}
