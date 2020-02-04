package com.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.constant.Constant;
import com.resource.dto.EmployeeDetailResponse;
import com.resource.dto.EmployeeDetailResponseDto;
import com.resource.dto.LoginRequestDto;
import com.resource.dto.LoginResponseDto;
import com.resource.dto.ResourceListResponseDto;
import com.resource.entity.Employee;
import com.resource.entity.EmployeeCourse;
import com.resource.entity.EmployeeInterview;
import com.resource.entity.EmployeeProject;
import com.resource.entity.EmployeeSkill;
import com.resource.exception.EmployeeNotFoundException;
import com.resource.repository.EmployeeCourseRepository;
import com.resource.repository.EmployeeInterviewRepository;
import com.resource.repository.EmployeeProjectRepository;
import com.resource.repository.EmployeeRepository;
import com.resource.repository.EmployeeSkillRepository;
import com.resource.repository.SkillRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * This will have the authenticateEmployee and listOfBenchResource service
 * implementation.
 * 
 * @since 2020-01-28.
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	EmployeeProjectRepository employeeProjectRepository;

	@Autowired
	EmployeeInterviewRepository employeeInterviewRepository;

	@Autowired
	EmployeeCourseRepository employeeCourseRepository;

	@Autowired
	SkillRepository skillRepository;

	/**
	 * The Constant log.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-01-28. This method will authenticate the employee.
	 * @param loginRequestDto - details of the user login
	 * 
	 * @return LoginResponseDto which has status message and statusCode.
	 * @throws EmployeeNotFoundException it will throw the exception if the employee
	 *                                   is not there.
	 */
	@Override
	public LoginResponseDto authenticateEmployee(LoginRequestDto loginRequestDto) throws EmployeeNotFoundException {
		Optional<Employee> employee = employeeRepository.findByMailIdAndPassword(loginRequestDto.getMailId(),
				loginRequestDto.getPassword());
		if (!employee.isPresent()) {
			logger.error("employee not found");
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		} else {
			LoginResponseDto loginResponseDto = new LoginResponseDto();
			loginResponseDto.setRole(employee.get().getRole().getRoleName());
			loginResponseDto.setEmployeeId(employee.get().getEmployeeId());
			logger.info("Authentication Successful");
			return loginResponseDto;
		}
	}

	/**
	 * This method is used to fetch the employees professional details like
	 * skillset,attended interviews,worked on projects and courses enrolled
	 * 
	 * @author Chethana
	 * @param employeeId is of type Long data type
	 * @return EmployeeDetailResponseDto which has message,statusCode and
	 *         EmployeeDetailRespons response
	 * @throws EmployeeNotFoundException will be thrown the if the employee is not
	 *                                   there.
	 */
	public EmployeeDetailResponseDto getEmployeeDetails(Long employeeId) throws EmployeeNotFoundException {
		log.info("Entering into getEmployeeDetails of EmployeeServiceImpl");
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			log.error("Exception Occured in getEmployeeDetails of EmployeeServiceImpl:" + Constant.EMPLOYEE_NOT_FOUND);
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		}
		log.debug("Entering into getEmployeeDetails of EmployeeServiceImpl");
		Optional<List<EmployeeSkill>> employeeSkillList = employeeSkillRepository.findByEmployee(employee.get());
		
		
		EmployeeDetailResponse employeeDetailResponse = new EmployeeDetailResponse();
		if (!employeeSkillList.isPresent()) {
			employeeDetailResponse.setEmployeeSkillList(new ArrayList<>());
		} else {
			employeeDetailResponse.setEmployeeSkillList(employeeSkillList.get());
		}
		Optional<List<EmployeeProject>> employeeProjectList = employeeProjectRepository.findByEmployee(employee.get());
		
		if (!employeeProjectList.isPresent()) {
			employeeDetailResponse.setEmployeeProjectList(new ArrayList<>());
		} else {
			List<EmployeeProject> employeeProjectsList = employeeProjectList.get();
			employeeProjectsList = employeeProjectsList.stream()
					.filter(employeeProject -> !employeeProject.getProject().getProjectName().equalsIgnoreCase("bench"))
					.collect(Collectors.toList());
			employeeDetailResponse.setEmployeeProjectList(employeeProjectsList);
		}
		Optional<List<EmployeeInterview>> employeeInterviewList = employeeInterviewRepository
				.findByEmployee(employee.get());
		if (!employeeInterviewList.isPresent()) {
			employeeDetailResponse.setEmployeeInterviewList(new ArrayList<>());
		} else {
			employeeDetailResponse.setEmployeeInterviewList(employeeInterviewList.get());
		}
		Optional<List<EmployeeCourse>> employeeCourseList = employeeCourseRepository.findByEmployee(employee.get());

		if (!employeeCourseList.isPresent()) {
			employeeDetailResponse.setEmployeeCourseList(new ArrayList<>());
		} else {
			employeeDetailResponse.setEmployeeCourseList(employeeCourseList.get());
		}
		EmployeeDetailResponseDto employeeDetailResponseDto= new EmployeeDetailResponseDto();
		employeeDetailResponseDto.setEmployeeDetailResponse(employeeDetailResponse);
		return employeeDetailResponseDto;
	}

	/**
	 * 
	 * @author Sri Keerthna.
	 * @since 2020-01-28. In this method list of bench resources are fetched from
	 *        database and displayed.
	 * @return ResourceListResponseDto which has bench resource details.
	 * @throws EmployeeNotFoundException if the bench resource list is empty then it
	 *                                   will throw an error.
	 */
	@Override
	public List<ResourceListResponseDto> resourceList() throws EmployeeNotFoundException {
		List<ResourceListResponseDto> responseDto = new ArrayList<>();
		List<Employee> employeeList = employeeRepository.findByStatus(Constant.BENCH_RESOURCES);
		if (employeeList.isEmpty()) {
			logger.error("employee list is empty");
			throw new EmployeeNotFoundException(Constant.EMPLOYEE_NOT_FOUND);
		} else {
			employeeList.forEach(list -> {
				ResourceListResponseDto dto = new ResourceListResponseDto();
				BeanUtils.copyProperties(list, dto);
				responseDto.add(dto);
			});
		}
		logger.info("Got the employee list");
		return responseDto;
	}

	/**
	 * Get the employee details based on the employeeId
	 * 
	 * @param employeeId - Id of the employee
	 * @return optional employee object.
	 * @author Govindasamy.C
	 * 
	 */
	@Override
	public Optional<Employee> getEmployeeById(Long employeeId) {
		return employeeRepository.findById(employeeId);
	}

}
