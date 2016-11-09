package by.inventain.dao;

import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public interface MeetingRepository extends CrudRepository<Meeting, Integer> {
    @Query("SELECT count(e) > 0 FROM Company e WHERE " +
            "e.openTime <= :startTime " +
            "AND e.closeTime >= :endTime " +
            "AND e.id = :companyId")
    boolean checkCompanyTime(@Param("startTime") LocalTime startTime,
                             @Param("endTime") LocalTime endTime,
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
}
