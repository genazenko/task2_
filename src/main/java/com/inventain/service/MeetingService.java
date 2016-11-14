package com.inventain.service;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.inventain.dao.CompanyRepository;
import com.inventain.dao.EmployeeRepository;
import com.inventain.dao.MeetingRepository;
import com.inventain.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Transactional
    public int insert(Meeting meeting, int companyId) {
        boolean checkCompanyAndEmployee = meetingRepository.checkCompanyAndEmployee(companyId, meeting.getSubmittedBy().getEmpId());
        if (!checkCompanyAndEmployee) {
            return -1;
        }
        meeting.setCompany(companyRepository.findOne(companyId));
        meeting.setSubmittedBy(employeeRepository.findOne(meeting.getSubmittedBy().getEmpId()));
        LocalDateTime startTime = meeting.getStartTime();
        LocalDateTime endTime = meeting.getEndTime();
        boolean checkInCompanyTime = meetingRepository.checkCompanyTime(startTime.toLocalTime().toString(), endTime.toLocalTime().toString(), meeting.getCompany().getId());
        boolean checkOverlap = meetingRepository.checkOverlap(startTime, endTime, meeting.getCompany());
        if (checkInCompanyTime && checkOverlap) {
            meetingRepository.save(meeting);
            return meeting.getId();
        }
        return -1;
    }

    @Transactional
    public Meeting getById(int id) {
        Meeting meeting = meetingRepository.findOne(id);
        return meeting;
    }

    @Transactional
    public Map<LocalDate, Collection<Meeting>> getAllByCompanyId(int companyId) {
        List<Meeting> list = meetingRepository.findAllByCompanyIdOrderByStartTime(companyId);
        return getResultAsMap(list);
    }

    @Transactional
    public Map<LocalDate, Collection<Meeting>> getAllInTime(int companyId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Meeting> list = meetingRepository.findAllInTime(companyId, startTime, endTime);
        return getResultAsMap(list);
    }

    @Transactional
    public Map<String, List<Meeting>> insertMeetings(int companyId, List<Meeting> meetingsList) {
        List<Meeting> validList = meetingsList.stream().filter(meeting -> insert(meeting, companyId) != -1)
                .collect(Collectors.toList());
        List<Meeting> invalidList = meetingsList.parallelStream().filter(meeting -> validList.contains(meeting) == false)
                .collect(Collectors.toList());
        Map<String, List<Meeting>> result = new HashMap<>();
        result.put("valid", validList);
        result.put("invalid", invalidList);
        return result;
    }

    private Map<LocalDate, Collection<Meeting>> getResultAsMap(List<Meeting> list) {
        ListMultimap<LocalDate, Meeting> result = Multimaps.newListMultimap(
                new TreeMap<>(), ArrayList::new);
        list.stream().forEach(meeting -> result.put(meeting.getStartTime().toLocalDate(), meeting));
        return result.asMap();
    }
}
