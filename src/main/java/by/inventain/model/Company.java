package by.inventain.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by 123 on 06.11.2016.
 */
@JsonSerialize(using = CompanyCustomSerializer.class)
@Entity
@Table(name = "COMPANY")
public class Company {
    @Id
    @Column
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private LocalTime openTime;
    @Column
    private LocalTime closeTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
