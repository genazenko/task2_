package by.inventain.dao;

import by.inventain.model.Company;

import java.util.List;

/**
 * Created by 123 on 06.11.2016.
 */
public interface CompanyDAO {
    public int insert (Company company);
    public void update (Company company);
    public void delete (Company company);
    public Company getById(int id);
    public List<Company> getAll();
}