package by.inventain.dao;

import by.inventain.model.Company;

import java.time.LocalDateTime;
import java.util.List;


public interface CompanyDAO {
    public int insert(Company company);

    public void update(Company company);

    public void delete(Company company);

    public Company getById(int id);

    public Company getInTime(int id, LocalDateTime startTime, LocalDateTime endTime);

    public List<Company> getAll();
}
