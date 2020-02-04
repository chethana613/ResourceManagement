package com.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByMailIdAndPassword(String mailId, String password);


	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-28. 
	 * By checking the status of employees it will filter the bench resources from the database.
	 * @param status - current project status
	 * @return list of employees 
	 */
	List<Employee> findByStatus(String status);

}
