package com.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.entity.Employee;
import com.resource.entity.EmployeeCourse;
@Repository
public interface EmployeeCourseRepository extends JpaRepository<EmployeeCourse, Integer>{

	List<EmployeeCourse> findAllByEmployeeEmployeeId(Long employeeId);

	Optional<List<EmployeeCourse>> findByEmployee(Employee employee);

}
