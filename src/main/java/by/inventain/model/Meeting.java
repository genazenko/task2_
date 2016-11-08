package by.inventain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDateTime;


@JsonSerialize(using = MeetingCustomSerializer.class)
@Entity
@Table(name = "MEETING")
public class Meeting {
    @Id
    @Column
    @GeneratedValue
    private int id;
    @Column
    private LocalDateTime startTime;
    @Column
    private int duration;
    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
    @Column
    private String submittedBy;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }
}
