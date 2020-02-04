package com.resource.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.constant.Constant;
import com.resource.dto.EmployeeDetailResponseDto;
import com.resource.dto.LoginRequestDto;
import com.resource.dto.LoginResponseDto;
import com.resource.dto.ResourceListResponseDto;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * This will have the authenticateEmployee and listOfBenchResource APIs.
 * 
 * @since 2020-01-28.
 */
@RestController
@RequestMapping("/employees")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
public class EmployeeController {

	/**
	 * This will inject the methods in the employeeService interface.
	 */
	@Autowired
	EmployeeService employeeService;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-01-28. This method will authenticate the employee.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * 
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> authenticateEmployee(@RequestBody LoginRequestDto loginDto)
			throws EmployeeNotFoundException {
		LoginResponseDto loginResponseDto = employeeService.authenticateEmployee(loginDto);
		logger.info("Authentication Successful");
		loginResponseDto.setMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}

	/**
	 * This method is used to fetch the employees professional details like
	 * skillset,attended interviews,worked on projects
	 * 
	 * @author Chethana
	 * @param employeeID isof type Long data type
	 * @return EmployeeDetailResponseDto which has message,statusCode and
	 *         EmployeeDetailRespons response
	 * @throws EmployeeNotFoundException will be thrown the if the employee is not
	 *                                   there.
	 */
	@GetMapping("/{employeeID}")
	public ResponseEntity<EmployeeDetailResponseDto> getEmployeeDetails(@PathVariable Long employeeID)
			throws EmployeeNotFoundException {
		log.info("Entering into EmployeeDetailResponseDto method of EmployeeController");
		EmployeeDetailResponseDto employeeDetailResponseDto = employeeService.getEmployeeDetails(employeeID);
		employeeDetailResponseDto.setMessage(Constant.SUCCESS_MESSAGE);
		employeeDetailResponseDto.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(employeeDetailResponseDto, HttpStatus.OK);
	}

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-28. In this method list of bench resources are fetched from
	 *        database and displayed.
	 * @return ResourceListResponseDto which has bench resource details.
	 * @throws EmployeeNotFoundException if the bench resource list is empty then it
	 *                                   will throw an error.
	 */
	@GetMapping
	public ResponseEntity<List<ResourceListResponseDto>> resourceList() throws EmployeeNotFoundException {
		logger.info("Entered into resourseList method");
		return new ResponseEntity<>(employeeService.resourceList(), HttpStatus.OK);
	}
}
