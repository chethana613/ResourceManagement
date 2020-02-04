package com.resource.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.entity.Employee;
import com.resource.entity.EmployeeSkill;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Integer>{

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-30.
	 * This method will filter the skills for that particular employee id.
	 * @param employee - employee object
	 * @return list of employee skills
	 */
	Optional<List<EmployeeSkill>> findByEmployee(Employee employee);


}
