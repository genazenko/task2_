package by.inventain.dao;

import by.inventain.model.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by developer on 08/11/2016.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}
