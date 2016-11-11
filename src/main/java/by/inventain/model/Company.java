package by.inventain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "COMPANY")
public class Company {
    @Id
    @Column
    @GeneratedValue
    @JsonIgnore
    private int id;
    @Column
    @NotEmpty
    private String name;
    @Column(columnDefinition = "TIME")
    private LocalTime openTime;
    @Column(columnDefinition = "TIME")
    private LocalTime closeTime;
    @OneToMany
    @JsonIgnore
    private List<Meeting> meetings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
