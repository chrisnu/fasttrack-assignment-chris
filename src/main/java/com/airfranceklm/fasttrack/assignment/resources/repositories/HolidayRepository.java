package com.airfranceklm.fasttrack.assignment.resources.repositories;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
    List<Holiday> findByEmployeeId(String employeeId);
    @Query("SELECT count(h) FROM Holiday h WHERE h.startOfHoliday <= ?2 AND h.endOfHoliday >= ?1")
    int overlappingHolidays(LocalDateTime start, LocalDateTime end);

    @Query("SELECT count(h) FROM Holiday h WHERE h.startOfHoliday <= ?2 AND h.endOfHoliday >= ?1 AND h.id != ?3")
    int overlappingHolidays(LocalDateTime start, LocalDateTime end, UUID exceptHolidayId);
}
