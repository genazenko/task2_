package by.inventain.service;

import by.inventain.dao.CompanyRepository;
import by.inventain.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public int insert(Company company) {
        checkCompanyTime(company);
        checkOverlap(company);
        if (company.getMeetings() != null) {
            for (int i = 0; i < company.getMeetings().size(); i++) {
                company.getMeetings().get(i).setCompany(company);
            }
        }
        companyRepository.save(company);
        return company.getId();
    }

    @Transactional
    public Company getById(int id) {
        Company company = companyRepository.findOne(id);
        if (company != null)
            company.setMeetings(companyRepository.getMeetings(company));
        return company;
    }

    @Transactional
    public Company getCompanyInfo(int id) {
        Company company = companyRepository.findOne(id);
        if (company != null) company.getMeetings().clear();
        return company;
    }

    @Transactional
    public Company getInTime(int id, LocalDateTime startTime, LocalDateTime endTime) {
        Company company = companyRepository.findOne(id);
        company.setMeetings(companyRepository.getMeetingsInTime(company, startTime, endTime));
        return company;
    }

    private void checkOverlap(Company company) {
        if (company.getMeetings() == null) return;
        for (int i = 0; i < company.getMeetings().size(); i++) {
            LocalDateTime startTime = company.getMeetings().get(i).getStartTime();
            LocalDateTime endTime = company.getMeetings().get(i).getEndTime();
            for (int j = i + 1; j < company.getMeetings().size(); j++) {
                LocalDateTime tmpStart = company.getMeetings().get(j).getStartTime();
                LocalDateTime tmpEnd = company.getMeetings().get(j).getEndTime();
                if (startTime.compareTo(tmpStart) == 0 || startTime.compareTo(tmpStart) == -1 && endTime.compareTo(tmpStart) == 1) {
                    company.getMeetings().remove(j--);
                    continue;
                }
                if (endTime.compareTo(tmpEnd) == 0 || endTime.compareTo(tmpEnd) == 1 && startTime.compareTo(tmpEnd) == -1) {
                    company.getMeetings().remove(j--);
                    continue;
                }
            }
        }
    }

    private void checkCompanyTime(Company company) {
        if (company.getMeetings() == null) return;
        for (int i = 0; i < company.getMeetings().size(); i++) {
            LocalTime startTime = company.getMeetings().get(i).getStartTime().toLocalTime();
            LocalTime endTime = company.getMeetings().get(i).getEndTime().toLocalTime();
            if (startTime.compareTo(company.getOpenTime()) == -1 || endTime.compareTo(company.getCloseTime()) == 1) {
                company.getMeetings().remove(i--);
            }
        }
    }
}
