package com.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.entity.Employee;
import com.resource.entity.EmployeeProject;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Integer> {

	Optional<List<EmployeeProject>> findByEmployee(Employee employee);

	List<EmployeeProject> findAllByEmployeeEmployeeId(Long employeeId);

}
