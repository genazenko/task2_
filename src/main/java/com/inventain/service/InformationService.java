package com.inventain.service;


import com.inventain.dao.MeetingRepository;
import com.inventain.model.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InformationService {
    @Autowired
    private MeetingRepository meetingRepository;

    @Transactional
    public List<Information> getAllInf() {
        List<Information> result = meetingRepository.getAllInf();
        return result;
    }
}
