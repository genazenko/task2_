package by.inventain.dao;

import by.inventain.model.Company;
import org.springframework.data.repository.CrudRepository;


public interface CompanyRepository extends CrudRepository<Company, Integer> {
}
