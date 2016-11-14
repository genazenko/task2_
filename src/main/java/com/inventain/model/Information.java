package com.inventain.model;


import java.time.LocalDateTime;
import java.time.LocalTime;

public class Information {
    private int companyId;
    private String companyName;
    private LocalTime openTime;
    private LocalTime closeTime;
    private int employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private int meetingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Information(int companyId, String companyName, LocalTime openTime, LocalTime closeTime,
                       int employeeId, String employeeFirstName, String employeeLastName,
                       int meetingId, LocalDateTime startTime, LocalDateTime endTime) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.employeeId = employeeId;
        this.employeeFirstName = employeeFirstName;
        this.employeeLastName = employeeLastName;
        this.meetingId = meetingId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
