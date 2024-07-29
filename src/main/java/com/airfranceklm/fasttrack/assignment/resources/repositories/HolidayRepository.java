package com.airfranceklm.fasttrack.assignment.resources.repositories;

import com.airfranceklm.fasttrack.assignment.resources.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface HolidayRepository extends JpaRepository<Holiday, UUID> {
    List<Holiday> findByEmployeeId(String employeeId);
}
