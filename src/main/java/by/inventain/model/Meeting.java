package by.inventain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;


@Entity
@Table(name = "MEETING")
public class Meeting {
    @Id
    @Column
    @GeneratedValue
    @JsonIgnore
    private int id;
    @Column
    private LocalDateTime startTime;
    @Column
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "companyId", nullable = false)
    @JsonIgnore
    private Company company;
    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee submittedBy;

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public Employee getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(Employee submittedBy) {
        this.submittedBy = submittedBy;
    }
}
