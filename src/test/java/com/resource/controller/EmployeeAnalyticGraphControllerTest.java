package com.resource.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.resource.dto.ResponseDto;
import com.resource.dto.SkillDto;
import com.resource.entity.Employee;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.exception.InvalidAnalyticTypeException;
import com.resource.service.EmployeeAnalyticGraphService;
import com.resource.service.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeAnalyticGraphControllerTest {

	@InjectMocks
	EmployeeAnalyticGraphController employeeAnalyticGraphController;

	@Mock
	EmployeeService employeeService;

	@Mock
	EmployeeAnalyticGraphService employeeAnalyticGraphService;

	Employee employee = new Employee();
	SkillDto skillDto = new SkillDto();

	@Before
	public void init() {
		employee.setEmployeeId(1L);

		skillDto.setSkillName("Java");
		skillDto.setPercentage(75.00);
	}

	@Test
	public void testGetHealthCheckDetailsByEmployeeId() throws InvalidAnalyticTypeException, EmployeeNotFoundException {
		List<SkillDto> skillDtos = new ArrayList<>();
		skillDtos.add(skillDto);

		when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));
		when(employeeAnalyticGraphService.getAnalyticDetailsByEmployeeId("Tech", 1L)).thenReturn(skillDtos);

		ResponseEntity<ResponseDto> response = employeeAnalyticGraphController.getHealthCheckDetailsByEmployeeId(1L,
				"Tech");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
 
	@Test(expected = EmployeeNotFoundException.class)
	public void testGetHealthCheckDetailsByEmployeeIdForException()
			throws InvalidAnalyticTypeException, EmployeeNotFoundException {
		when(employeeService.getEmployeeById(1L)).thenReturn(Optional.ofNullable(null));
		employeeAnalyticGraphController.getHealthCheckDetailsByEmployeeId(1L, "Tech");
	}
}
