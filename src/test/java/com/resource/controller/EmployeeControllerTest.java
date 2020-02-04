package com.resource.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.resource.constant.Constant;
import com.resource.dto.LoginRequestDto;
import com.resource.dto.LoginResponseDto;
import com.resource.dto.ResourceListResponseDto;
import com.resource.entity.Employee;
import com.resource.entity.Role;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.service.EmployeeService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeControllerTest {

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	List<Employee> employeeList = new ArrayList<>();
	List<ResourceListResponseDto> responseList = new ArrayList<>();
	ResourceListResponseDto resourceListResponseDto = new ResourceListResponseDto();
	Role role = new Role();
	Employee employee = new Employee();

	@Before
	public void setUp() {
		employee.setEmployeeId(1234567L);
		employee.setEmployeeName("Sri");
		employee.setMailId("Sri@gmail.com");
		employee.setPhoneNumber(7864579906L);
		employee.setStatus("Bench");

		employeeList.add(employee);

		resourceListResponseDto.setEmployeeId(employee.getEmployeeId());
		resourceListResponseDto.setEmployeeName(employee.getEmployeeName());
		resourceListResponseDto.setExperience(employee.getExperience());
		resourceListResponseDto.setMailId(employee.getMailId());
		resourceListResponseDto.setPhoneNumber(employee.getPhoneNumber());
		resourceListResponseDto.setStatus(employee.getStatus());

		responseList.add(resourceListResponseDto);
		role.setRoleId(1);
		role.setRoleName("Manager");

		employee.setEmployeeId(1L);
		employee.setMailId("priya@gmail.com");
		employee.setPassword("sri");
		employee.setRole(role);

		loginRequestDto.setMailId("priya@gmail.com");
		loginRequestDto.setPassword("sri");

		
		employeeList.add(employee);

		resourceListResponseDto.setEmployeeId(employee.getEmployeeId());
		resourceListResponseDto.setEmployeeName(employee.getEmployeeName());
		resourceListResponseDto.setExperience(employee.getExperience());
		resourceListResponseDto.setMailId(employee.getMailId());
		resourceListResponseDto.setPhoneNumber(employee.getPhoneNumber());
		resourceListResponseDto.setStatus(employee.getStatus());

		responseList.add(resourceListResponseDto);

	}

	@Test
	public void testResourceListPositive() throws EmployeeNotFoundException {
		logger.info("Entered into resourseList method");
		Mockito.when(employeeService.resourceList()).thenReturn(responseList);
		Integer result = employeeController.resourceList().getStatusCodeValue();
		Assert.assertEquals(200, result.intValue());
	}

	@Test
	public void authenticateEmployee() throws EmployeeNotFoundException {
		loginResponseDto.setMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		Mockito.when(employeeService.authenticateEmployee(loginRequestDto)).thenReturn(loginResponseDto);
		ResponseEntity<LoginResponseDto> result = employeeController.authenticateEmployee(loginRequestDto);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

}
