package com.inventain.dao;

import com.inventain.model.Company;
import org.springframework.data.repository.CrudRepository;


public interface CompanyRepository extends CrudRepository<Company, Integer> {
}
