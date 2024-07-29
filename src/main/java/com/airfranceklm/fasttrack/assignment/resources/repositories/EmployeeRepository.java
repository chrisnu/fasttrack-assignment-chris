package com.airfranceklm.fasttrack.assignment.resources.repositories;

import com.airfranceklm.fasttrack.assignment.resources.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
