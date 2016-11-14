package com.inventain.dao;

import com.inventain.model.Company;
import com.inventain.model.Information;
import com.inventain.model.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    @Query("SELECT count(e) = 1 FROM Company e WHERE " +
            "e.openTime <= PARSEDATETIME (:startTime, 'HH:mm') " +
            "AND e.closeTime >= PARSEDATETIME( :endTime, 'HH:mm') " +
            "AND e.id = :companyId")
    boolean checkCompanyTime(@Param("startTime") String startTime,
                             @Param("endTime") String endTime,
                             @Param("companyId") int id);

    @Query("SELECT count (e) = 0 FROM Meeting e WHERE " +
            "(e.startTime <= :startTime AND :startTime < e.endTime AND e.company = :company) " +
            "OR (e.startTime < :endTime AND :endTime <= e.endTime AND e.company = :company) ")
    boolean checkOverlap(@Param("startTime") LocalDateTime startTime,
                         @Param("endTime") LocalDateTime endTime,
                         @Param("company") Company company);

    List<Meeting> findAllByCompanyIdOrderByStartTime(int companyId);

    @Query("SELECT e FROM Meeting e WHERE " +
            "e.company = (SELECT comp FROM Company comp WHERE comp.id = ?1) " +
            "AND (e.startTime between ?2 and ?3) " +
            "AND (e.endTime between ?2 and ?3)")
    List<Meeting> findAllInTime(int companyId, LocalDateTime startTime, LocalDateTime endTime);

    @Query("SELECT count (e) = 1 FROM Employee e WHERE " +
            "e.company = (SELECT comp FROM Company comp WHERE comp.id = ?1) " +
            "AND e.empId = ?2")
    boolean checkCompanyAndEmployee(int companyId, int empId);

    @Query("SELECT NEW com.inventain.model.Information(c.id, c.name, c.openTime, c.closeTime," +
            " e.empId, e.firstName, e.lastName," +
            " m.id, m.startTime, m.endTime) FROM Meeting m join m.company c join m.submittedBy e " +
            "WHERE m.submittedBy = e AND e.company = c " +
            "ORDER BY m.startTime")
    List<Information> getAllInf();
}
