package com.resource.service;

import java.util.List;

import com.resource.entity.Course;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.exception.EmployeeSkillNotFoundException;

public interface EmployeeCourseService {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-30. In this method employee who are having skills with below
	 *        average and poor are filtered and recommending courses for those
	 *        skills.
	 * @param employeeId - Id of the employee
	 * @return List of courses will be shown for that particular employee id.
	 * @throws EmployeeSkillNotFoundException when no skills found for that
	 *                                        particular id.
	 * @throws EmployeeNotFoundException      when an employee is not found
	 */
	public List<Course> getEmployeeSkill(Long employeeId)
			throws EmployeeSkillNotFoundException, EmployeeNotFoundException;
}
