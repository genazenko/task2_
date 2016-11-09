package by.inventain.service;

import by.inventain.dao.CompanyRepository;
import by.inventain.dao.EmployeeRepository;
import by.inventain.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public int insert(Employee employee, int companyId) {
        if (companyRepository.findOne(companyId) == null) {
            return -1;
        }
        employee.setCompany(companyRepository.findOne(companyId));
        employeeRepository.save(employee);
        return employee.getEmpId();
    }

    @Transactional
    public Employee getById(int id) {
        return employeeRepository.findOne(id);
    }
}
