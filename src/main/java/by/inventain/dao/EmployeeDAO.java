package by.inventain.dao;

import by.inventain.model.Employee;

import java.util.List;

/**
 * Created by developer on 08/11/2016.
 */
public interface EmployeeDAO {
    public void delete (Employee employee);
    public void update (Employee employee);
    public int insert (Employee employee);
    public Employee getById (int id);
    public List<Employee> getAll ();
}
