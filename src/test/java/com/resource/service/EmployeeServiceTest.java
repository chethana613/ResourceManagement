package com.resource.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.resource.constant.Constant;
import com.resource.dto.EmployeeDetailResponseDto;
import com.resource.dto.LoginRequestDto;
import com.resource.dto.LoginResponseDto;
import com.resource.dto.ResourceListResponseDto;
import com.resource.entity.Employee;
import com.resource.entity.EmployeeCourse;
import com.resource.entity.EmployeeInterview;
import com.resource.entity.EmployeeProject;
import com.resource.entity.EmployeeSkill;
import com.resource.entity.Project;
import com.resource.entity.Role;
import com.resource.entity.Skill;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.repository.EmployeeCourseRepository;
import com.resource.repository.EmployeeInterviewRepository;
import com.resource.repository.EmployeeProjectRepository;
import com.resource.repository.EmployeeRepository;
import com.resource.repository.EmployeeSkillRepository;
import com.resource.repository.SkillRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeServiceTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Mock
	EmployeeRepository employeeRepository;
	
	@Mock
	EmployeeSkillRepository employeeSkillRepository;

	@Mock
	EmployeeProjectRepository employeeProjectRepository;

	@Mock
	EmployeeInterviewRepository employeeInterviewRepository;
	
	@Mock
	EmployeeCourseRepository employeeCourseRepository;

	@Mock
	SkillRepository skillRepository;
	
	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	Role role = new Role();
	Employee employee = new Employee();
	Employee employee1 = new Employee();
	List<Employee> employeeList = new ArrayList<>();
	ResourceListResponseDto resourceListResponseDto= new ResourceListResponseDto();

	List<EmployeeSkill> employeeSkillList= new ArrayList<>();
	EmployeeSkill employeeSkill= new EmployeeSkill();
	
	List<EmployeeProject> employeeProjectList= new ArrayList<EmployeeProject>();
	EmployeeProject employeeProject= new EmployeeProject();
	
	List<EmployeeInterview> employeeInterviewList= new ArrayList<EmployeeInterview>();
	EmployeeInterview employeeInterview= new EmployeeInterview();
	
	List<EmployeeCourse> employeeCourseList= new ArrayList<EmployeeCourse>();
	EmployeeCourse employeeCourse= new EmployeeCourse();
	
	@Before
	public void init() {
		employee.setEmployeeId(1234567L);
		employee.setEmployeeName("Sri");
		employee.setExperience(3);
		employee.setMailId("Sri@gmail.com");
		employee.setPhoneNumber(7864579906L);
		employee.setStatus("Bench");

		employeeList.add(employee);


		role.setRoleId(1);
		role.setRoleName("Manager");

		employee.setEmployeeId(1L);
		employee.setMailId("priya@gmail.com");
		employee.setPassword("sri");
		employee.setRole(role);

		loginRequestDto.setMailId("priya@gmail.com");
		loginRequestDto.setPassword("sri");

		employeeList.add(employee);

		Skill skill= new Skill();
		employeeSkill.setEmployee(employee);
		employeeSkill.setEmployeeSkillId(1L);
		employeeSkill.setSkill(skill);
	}

	@Test
	public void testResourceListPositive() throws EmployeeNotFoundException {
		Mockito.when(employeeRepository.findByStatus(Constant.BENCH_RESOURCES)).thenReturn(employeeList);
		logger.info("Got the employee list");
		BeanUtils.copyProperties(employeeList, resourceListResponseDto);
		List<ResourceListResponseDto> result = employeeServiceImpl.resourceList();
		assertEquals(2, result.size());
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void testResourceListNegative() throws EmployeeNotFoundException {
		List<Employee> list = new ArrayList<>();
		Mockito.when(employeeRepository.findByStatus(Constant.BENCH_RESOURCES)).thenReturn(list);
		logger.error("employee list is empty");
		employeeServiceImpl.resourceList();
	
	}

	@Test
	public void testAuthenticateEmployeePositive() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findByMailIdAndPassword("priya@gmail.com", "sri"))
				.thenReturn(Optional.of(employee));
		LoginResponseDto result = employeeServiceImpl.authenticateEmployee(loginRequestDto);
		assertEquals("Manager", result.getRole());
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testAuthenticateEmployeeForEmployeeNotFound()
			throws EmployeeNotFoundException {

		Mockito.when(employeeRepository.findByMailIdAndPassword("hari@gmail.com", "sri"))
				.thenReturn(Optional.of(employee));
		employeeServiceImpl.authenticateEmployee(loginRequestDto);

	}

	@Test(expected = EmployeeNotFoundException.class)
	public void testgetEmployeeDetailsNegative() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		employeeServiceImpl.getEmployeeDetails(13L);
	}
	
	@Test
	public void testgetEmployeeDetailsNegativeSkill() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(employeeSkillRepository.findByEmployee(employee1)).thenReturn(Optional.of(employeeSkillList));
		EmployeeDetailResponseDto employeeDetailResponseDto=employeeServiceImpl.getEmployeeDetails(1L);
		assertEquals(0,employeeDetailResponseDto.getEmployeeDetailResponse().getEmployeeSkillList().size());
	}
	
	@Test
	public void testgetEmployeeDetailsPositiveSkill() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Skill skill= new Skill();
		employeeSkill.setEmployee(employee);
		employeeSkill.setEmployeeSkillId(1L);
		employeeSkill.setSkill(skill);
		employeeSkillList.add(employeeSkill);
		Mockito.when(employeeSkillRepository.findByEmployee(employee)).thenReturn(Optional.of(employeeSkillList));
		
		EmployeeDetailResponseDto employeeDetailResponseDto=employeeServiceImpl.getEmployeeDetails(1L);
		assertEquals(1,employeeDetailResponseDto.getEmployeeDetailResponse().getEmployeeSkillList().size());
	}
	
	@Test
	public void testgetEmployeeDetailsNegativeProject() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(employeeSkillRepository.findByEmployee(employee)).thenReturn(Optional.of(employeeSkillList));
		Mockito.when(employeeProjectRepository.findByEmployee(employee1)).thenReturn(Optional.of(employeeProjectList));
		EmployeeDetailResponseDto employeeDetailResponseDto=employeeServiceImpl.getEmployeeDetails(1L);
		assertEquals(0,employeeDetailResponseDto.getEmployeeDetailResponse().getEmployeeProjectList().size());
	}
	@Test
	public void testgetEmployeeDetailsPositiveProject() throws EmployeeNotFoundException{
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(employeeSkillRepository.findByEmployee(employee)).thenReturn(Optional.of(employeeSkillList));
		Project project= new Project();
		project.setProjectName("bench");
		employeeProject.setEmployee(employee);
		employeeProject.setEmployeeProjectId(1L);
		employeeProject.setProject(project);
		employeeProjectList.add(employeeProject);
		Mockito.when(employeeProjectRepository.findByEmployee(employee)).thenReturn(Optional.of(employeeProjectList));
		EmployeeDetailResponseDto employeeDetailResponseDto=employeeServiceImpl.getEmployeeDetails(1L);
		assertEquals(1,employeeDetailResponseDto.getEmployeeDetailResponse().getEmployeeProjectList().size());
	}

}
