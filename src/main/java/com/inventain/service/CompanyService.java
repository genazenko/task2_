package com.inventain.service;

import com.inventain.dao.CompanyRepository;
import com.inventain.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public int insert(Company company) {
        companyRepository.save(company);
        return company.getId();
    }

    @Transactional
    public Company getCompanyInfo(int id) {
        Company company = companyRepository.findOne(id);
        return company;
    }

}
