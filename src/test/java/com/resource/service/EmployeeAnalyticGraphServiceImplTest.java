package com.resource.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.resource.dto.SkillDto;
import com.resource.entity.Course;
import com.resource.entity.Employee;
import com.resource.entity.EmployeeCourse;
import com.resource.entity.EmployeeInterview;
import com.resource.entity.EmployeeProject;
import com.resource.entity.EmployeeSkill;
import com.resource.entity.Interview;
import com.resource.entity.Project;
import com.resource.entity.Rating;
import com.resource.entity.Skill;
import com.resource.exception.InvalidAnalyticTypeException;
import com.resource.repository.EmployeeCourseRepository;
import com.resource.repository.EmployeeInterviewRepository;
import com.resource.repository.EmployeeProjectRepository;
import com.resource.repository.EmployeeSkillRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeAnalyticGraphServiceImplTest {

	@InjectMocks
	EmployeeAnalyticGraphServiceImpl employeeAnalyticGraphServiceImpl;

	@Mock
	EmployeeSkillRepository employeeSkillRepository;

	@Mock
	EmployeeInterviewRepository employeeInterviewRepository;

	@Mock
	EmployeeCourseRepository employeeCourseRepository;

	@Mock
	EmployeeProjectRepository employeeProjectRepository;

	Employee employee = new Employee();
	EmployeeInterview employeeInterview = new EmployeeInterview();
	EmployeeSkill employeeSkill = new EmployeeSkill();
	Skill skill = new Skill();
	Rating rating = new Rating();
	Course course = new Course();
	Project project = new Project();
	EmployeeCourse employeeCourse = new EmployeeCourse();
	EmployeeProject employeeProject = new EmployeeProject();

	Interview interview = new Interview();
	
	List<EmployeeInterview> employeeInterviews = new ArrayList<>();

	@Before
	public void init() {
		employee.setEmployeeId(1L);
		
		skill.setSkillId(1L);
		skill.setSkillName("Java");
		
		rating.setRatingId(1L);
		rating.setRatingScale("Poor");
		
		course.setCourseId(1);
		
		project.setProjectId(1L);
		project.setProjectName("Banking Project");
		
		interview.setInterviewId(1L);
		
		employeeSkill.setSkill(skill);
		employeeSkill.setEmployee(employee);
		employeeSkill.setEmployeeSkillId(1L);
		
		employeeCourse.setCourse(course);
		
		employeeProject.setEmployee(employee);
		employeeProject.setProject(project);
		
		employeeInterview.setRating(rating);
		employeeInterview.setInterview(interview);
		employeeInterview.setEmployee(employee);
		employeeInterview.setEmployeeSkill(employeeSkill);
		
		employeeInterviews.add(employeeInterview);

	}

	@Test
	public void testGetAnalyticDetailsByEmployeeIdForTech() throws InvalidAnalyticTypeException {
		when(employeeInterviewRepository.findAllByEmployeeEmployeeId(1L)).thenReturn(employeeInterviews);
		List<SkillDto> response = employeeAnalyticGraphServiceImpl.getAnalyticDetailsByEmployeeId("Tech", 1L);
		assertThat(response).hasSize(1);
	}
	
	@Test
	public void testGetAnalyticDetailsByEmployeeIdForTechBar() throws InvalidAnalyticTypeException {
		when(employeeInterviewRepository.findAllByEmployeeEmployeeId(1L)).thenReturn(employeeInterviews);
		List<SkillDto> response = employeeAnalyticGraphServiceImpl.getAnalyticDetailsByEmployeeId("Techbar", 1L);
		assertThat(response).hasSize(1);
	}
	
	@Test
	public void testGetAnalyticDetailsByEmployeeIdForOverall() throws InvalidAnalyticTypeException {
		List<EmployeeCourse> employeeCourses = new ArrayList<>();
		employeeCourses.add(employeeCourse);
		
		List<EmployeeProject> employeeProjects = new ArrayList<>();
		employeeProjects.add(employeeProject);
		
		when(employeeCourseRepository.findAllByEmployeeEmployeeId(1L)).thenReturn(employeeCourses);
		when(employeeProjectRepository.findAllByEmployeeEmployeeId(1L)).thenReturn(employeeProjects);

		List<SkillDto> response = employeeAnalyticGraphServiceImpl.getAnalyticDetailsByEmployeeId("Overall", 1L);
		assertThat(response).hasSize(3);
	}
	
	@Test(expected = InvalidAnalyticTypeException.class)
	public void testGetAnalyticDetailsByEmployeeIdForException() throws InvalidAnalyticTypeException {
		employeeAnalyticGraphServiceImpl.getAnalyticDetailsByEmployeeId("Tech1", 1L);
	}
}
