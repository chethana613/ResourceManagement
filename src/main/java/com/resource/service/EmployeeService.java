package com.resource.service;

import java.util.List;
import java.util.Optional;

import com.resource.dto.EmployeeDetailResponseDto;
import com.resource.dto.LoginRequestDto;
import com.resource.dto.LoginResponseDto;
import com.resource.dto.ResourceListResponseDto;
import com.resource.entity.Employee;
import com.resource.exception.EmployeeNotFoundException;

public interface EmployeeService {

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-01-28. This method will authenticate the employee.
	 * @return LoginResponseDto which has status message and statusCode.
	 * @param loginRequestDto - details of the user login 
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 * 
	 */
	LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException;

	public EmployeeDetailResponseDto getEmployeeDetails(Long employeeId) throws EmployeeNotFoundException;

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-28. In this method list of bench resources are fetched from
	 *        database and displayed.
	 * @return ResourceListResponseDto which has bench resource details.
	 * @throws EmployeeNotFoundException if the bench resource list is empty then it
	 *                                   will throw an error.
	 */
	public List<ResourceListResponseDto> resourceList() throws EmployeeNotFoundException;

	Optional<Employee> getEmployeeById(Long employeeId);

}
