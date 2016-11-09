package by.inventain.dao;

import by.inventain.model.Company;
import by.inventain.model.Meeting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface CompanyRepository extends CrudRepository<Company, Integer> {

    @Query("SELECT e FROM Meeting e WHERE " +
            "e.company = :company AND " +
            "e.startTime >= :startTime AND " +
            ":endTime >= e.endTime " +
            "order by e.startTime")
    List<Meeting> getMeetingsInTime(@Param("company") Company company,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);

    @Query("SELECT e FROM Meeting e WHERE " +
            "e.company = :company " +
            "order by e.startTime")
    List<Meeting> getMeetings(@Param("company") Company company);
}
