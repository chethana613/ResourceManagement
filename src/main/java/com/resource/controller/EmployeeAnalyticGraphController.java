package com.resource.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resource.constant.Constant;
import com.resource.dto.ResponseDto;
import com.resource.dto.SkillDto;
import com.resource.entity.Employee;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.exception.InvalidAnalyticTypeException;
import com.resource.service.EmployeeAnalyticGraphService;
import com.resource.service.EmployeeService;

/**
 * EmployeeAnalyticGraphController Class we have the class for employee health
 * check for pie chart details such as Technology, Last 1 Year Transactions and
 * Cibil Score for Employee Project details average.
 * 
 * @author Govindasamy.C
 * @since 28-01-2020
 * @version V1.1
 *
 */
@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeAnalyticGraphController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeAnalyticGraphService employeeAnalyticGraphService;

	/**
	 * Get the health check charts for Technology wise, Last 1 year transactions and
	 * cibil score for employee projects.
	 * 
	 * @param employeeId - Id of the employee
	 * @param chartType  - chartType such as Technology wise, Last 1 year
	 *                   transactions and cibil score for employee project.
	 * @return - list of skill details
	 * @throws InvalidAnalyticTypeException - throws this exception when giving Invalid type
	 * @throws EmployeeNotFoundException - throws this exception while employee not found
	 */
	@GetMapping("/{employeeId}/charts")
	public ResponseEntity<ResponseDto> getHealthCheckDetailsByEmployeeId(@PathVariable Long employeeId,
			@RequestParam("chartType") String chartType)
			throws InvalidAnalyticTypeException, EmployeeNotFoundException {
		Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
		if (employee.isPresent()) {
			List<SkillDto> analyticResult = employeeAnalyticGraphService.getAnalyticDetailsByEmployeeId(chartType, employeeId);
			ResponseDto responseDto = new ResponseDto();
			responseDto.setData(analyticResult);
			responseDto.setStatusCode(HttpStatus.OK.value());
			responseDto.setMessage(Constant.CHART_SUCCESS);
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		} else {
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		}
	}
}
