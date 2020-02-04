package com.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.constant.Constant;
import com.resource.controller.EmployeeCourseController;
import com.resource.entity.Course;
import com.resource.entity.Employee;
import com.resource.entity.EmployeeSkill;
import com.resource.entity.Rating;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.exception.EmployeeSkillNotFoundException;
import com.resource.repository.CourseRepository;
import com.resource.repository.EmployeeRepository;
import com.resource.repository.EmployeeSkillRepository;
import com.resource.repository.RatingRepository;

@Service
public class EmployeeCourseServiceImpl implements EmployeeCourseService {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeCourseController.class);

	@Autowired
	EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EmployeeRepository employeeRepository;

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
	@Override
	public List<Course> getEmployeeSkill(Long employeeId)
			throws EmployeeSkillNotFoundException, EmployeeNotFoundException {
		List<Course> finalcourses = new ArrayList<>();
		List<Rating> ratingList = ratingRepository.findByRatingScaleOrRatingScaleContainingIgnoreCase("below average",
				"poor");
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		} else {
			Optional<List<EmployeeSkill>> employeeSkill = employeeSkillRepository.findByEmployee(employee.get());
			if (!employeeSkill.isPresent()) {
				throw new EmployeeSkillNotFoundException(Constant.EMPLOYEE_SKILL_NOT_FOUND);
			} else {
				logger.info("got the list of skills");
				List<EmployeeSkill> skillList = new ArrayList<>();
				skillList.addAll(employeeSkill.get().stream().filter(rate -> ratingList.contains(rate.getRating()))
						.collect(Collectors.toList()));
				for (int skill = 0; skill < skillList.size(); skill++) {
					List<Course> courses = courseRepository.findBySkill(skillList.get(skill).getSkill());
					finalcourses.addAll(courses);
				}
			}
		}
		return finalcourses;

	}

}
