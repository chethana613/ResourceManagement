package com.resource.controller;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.resource.entity.Course;
import com.resource.entity.Skill;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.exception.EmployeeSkillNotFoundException;
import com.resource.service.EmployeeCourseService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeCourseControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@InjectMocks
	EmployeeCourseController employeeCourseController;

	@Mock
	EmployeeCourseService employeeCourseService;

	List<Course> courseList = new ArrayList<>();
	Course course = new Course();
	Skill skill = new Skill();

	@Before
	public void setUp() {
		course.setCourseId(1);
		course.setCourseName("advance java");
		skill.setSkillId(100L);
		skill.setSkillName("java");
		course.setSkill(skill);
	}

	@Test
	public void testGetEmployeeSkillPositive() throws EmployeeSkillNotFoundException, EmployeeNotFoundException {
		logger.info("Entered into getEmployeeSkill method");
		Mockito.when(employeeCourseService.getEmployeeSkill(1L)).thenReturn(courseList);
		ResponseEntity<List<Course>> result = employeeCourseController.getEmployeeSkill(1L);
		assertNotNull(result);
	}
}
