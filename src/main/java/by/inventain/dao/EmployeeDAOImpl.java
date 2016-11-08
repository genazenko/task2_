package by.inventain.dao;

import by.inventain.model.Employee;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by developer on 08/11/2016.
 */
public class EmployeeDAOImpl implements EmployeeDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public void delete(Employee employee) {

    }

    @Override
    public void update(Employee employee) {

    }

    @Override
    @Transactional
    public int insert(Employee employee) {
        employeeRepository.save(employee);
        return employee.getEmpId();
    }

    @Override
    @Transactional
    public Employee getById(int id) {
        return employeeRepository.findOne(id);
    }

    @Override
    public List<Employee> getAll() {
        return null;
    }
}
