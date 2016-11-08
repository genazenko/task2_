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
            "(e.startTime <= :startTime AND :startTime < e.endTime) " +
            "OR (e.startTime < :endTime AND :endTime <= e.endTime) " +
            "AND e.company = :company")
    boolean checkOverlap(@Param("startTime") LocalDateTime startTime,
                         @Param("endTime") LocalDateTime endTime,
                         @Param("company") Company company);

    List<Meeting> findAllByCompany(Company company);
}
