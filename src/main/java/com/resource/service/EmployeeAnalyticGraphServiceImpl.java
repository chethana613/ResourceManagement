package com.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.constant.Constant;
import com.resource.dto.SkillDto;
import com.resource.entity.EmployeeCourse;
import com.resource.entity.EmployeeInterview;
import com.resource.entity.EmployeeProject;
import com.resource.entity.EmployeeSkill;
import com.resource.exception.InvalidAnalyticTypeException;
import com.resource.repository.EmployeeCourseRepository;
import com.resource.repository.EmployeeInterviewRepository;
import com.resource.repository.EmployeeProjectRepository;
import com.resource.repository.EmployeeSkillRepository;

/**
 * EmployeeAnalyticGraphServiceImpl class to generate the charts for employee
 * based on the Technology wise rating, Overall employee activities details,
 * Projects and bench details graph.
 * 
 * @author Govindasamy.C
 * @version V1.1
 * @since 28-01-2020
 *
 */
@Service
public class EmployeeAnalyticGraphServiceImpl implements EmployeeAnalyticGraphService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeAnalyticGraphServiceImpl.class);

	@Autowired
	EmployeeSkillRepository employeeSkillRepository;

	@Autowired
	EmployeeInterviewRepository employeeInterviewRepository;

	@Autowired
	EmployeeCourseRepository employeeCourseRepository;

	@Autowired
	EmployeeProjectRepository employeeProjectRepository;

	/**
	 * Get the health check charts for Technology wise, Last 1 year transactions and
	 * cibil score for employee projects.
	 * 
	 * @param employeeId - Id of the employee
	 * @param chartType  - chartType such as Technology wise, Last 1 year
	 *                   transactions and cibil score for employee project.
	 * @return - list of skill details
	 * @throws InvalidAnalyticTypeException - throws this exception when giving
	 *                                      Invalid type
	 */
	@Override
	public List<SkillDto> getAnalyticDetailsByEmployeeId(String chartType, Long employeeId)
			throws InvalidAnalyticTypeException {
		logger.info("Get Analytics Graph details...");
		if (chartType.equalsIgnoreCase(Constant.TECHNOLOGY)) {
			return getTechnologyDetailsByEmployeeId(employeeId);
		} else if (chartType.equalsIgnoreCase(Constant.TECHNOLOGY_BAR)) {
			return getTechnologyBarDetailsByEmployeeId(employeeId);
		} else if (chartType.equalsIgnoreCase(Constant.OVERALL_ANALYTICS)) {
			return getOverallDetailsByEmployeeId(employeeId);
		} else {
			throw new InvalidAnalyticTypeException(Constant.INVALID_ANALYTIC_TYPE);
		}
	}

	/**
	 * Get the technology wise rating for the employee based on the ID (Pie Chart).
	 * 
	 * @param employeeId - Id of the employee
	 * @return list of skill name and percentage of the skill.
	 */
	private List<SkillDto> getTechnologyDetailsByEmployeeId(Long employeeId) {
		logger.debug("Get technology wise graph by employeeId...");
		List<EmployeeInterview> employeeSkills = employeeInterviewRepository.findAllByEmployeeEmployeeId(employeeId);
		List<SkillDto> skillDtos = new ArrayList<>();
		Map<EmployeeSkill, Integer> filteredSkills = employeeSkills.stream()
				.collect(Collectors.groupingBy(EmployeeInterview::getEmployeeSkill, Collectors.collectingAndThen(
						Collectors.mapping(EmployeeInterview::getEmployeeSkill, Collectors.toList()), List::size)));
		filteredSkills.forEach((employeeSkill, skillCount) -> {
			SkillDto skillDto = new SkillDto();
			skillDto.setSkillName(employeeSkill.getSkill().getSkillName());
			Double percentage = calculatePercentage(employeeSkills.size(), skillCount);
			skillDto.setPercentage(percentage);
			skillDtos.add(skillDto);
		});

		return skillDtos;
	}

	/**
	 * Get the technology wise rating for the employee based on the ID (Bar Chart).
	 * 
	 * @param employeeId
	 * @return list of skill name and percentage of the skill.
	 */
	private List<SkillDto> getTechnologyBarDetailsByEmployeeId(Long employeeId) {
		logger.debug("Get technology wise graph by employeeId...");
		List<EmployeeInterview> employeeInterviews = employeeInterviewRepository
				.findAllByEmployeeEmployeeId(employeeId);
		List<SkillDto> skillDtos = new ArrayList<>();
		Map<EmployeeSkill, Integer> filteredSkills = employeeInterviews.stream()
				.collect(Collectors.groupingBy(EmployeeInterview::getEmployeeSkill, Collectors.collectingAndThen(
						Collectors.mapping(EmployeeInterview::getEmployeeSkill, Collectors.toList()), List::size)));
		filteredSkills.forEach((employeeSkill, skillCount) -> {
			SkillDto skillDto = new SkillDto();
			skillDto.setSkillName(employeeSkill.getSkill().getSkillName());
			LongSummaryStatistics employees = employeeInterviews.stream()
					.filter(interview -> interview.getEmployeeSkill().getEmployeeSkillId()
							.equals(employeeSkill.getEmployeeSkillId()))
					.mapToLong(interview -> interview.getRating().getRatingId()).summaryStatistics();
			skillDto.setPercentage(employees.getAverage());
			skillDtos.add(skillDto);
		});

		return skillDtos;
	}

	/**
	 * Generate the overall employee activities based on the project details, bench
	 * activities and course training details.
	 * 
	 * @param employeeId
	 * @return list of the skillDto of skill name and percentage.
	 */
	private List<SkillDto> getOverallDetailsByEmployeeId(Long employeeId) {
		logger.debug("Get the overall employee activities...");
		List<SkillDto> skillDtos = new ArrayList<>();
		Integer totalCount = 0;

		// find all the employee courses based on the employee ID.
		List<EmployeeCourse> employeeCourses = employeeCourseRepository.findAllByEmployeeEmployeeId(employeeId);
		totalCount += employeeCourses.size();

		// find all the employee projects based on the employee ID.
		List<EmployeeProject> employeeProjects = employeeProjectRepository.findAllByEmployeeEmployeeId(employeeId);

		// find and filter all the bench projects based on the bench project type.
		List<EmployeeProject> benchProjects = employeeProjects.stream()
				.filter(employeeProject -> employeeProject.getProject().getProjectName().equals(Constant.BENCH))
				.collect(Collectors.toList());

		// find and filter all the real projects based on the project type.
		List<EmployeeProject> realProjects = employeeProjects.stream()
				.filter(employeeProject -> !employeeProject.getProject().getProjectName().equals(Constant.BENCH))
				.collect(Collectors.toList());

		// Total projects count add to total count.
		totalCount += employeeProjects.size();

		skillDtos.add(convertEntityToDto(employeeCourses, totalCount, Constant.COURSE_TRAINING));
		skillDtos.add(convertEntityToDto(benchProjects, totalCount, Constant.BENCH));
		skillDtos.add(convertEntityToDto(realProjects, totalCount, Constant.PROJECT));

		return skillDtos;
	}

	/**
	 * Calculate the Percentage of the technology wise rating.
	 * 
	 * @param totalCount
	 * @param ratingScale
	 * @return percentage value of the technology.
	 */
	private Double calculatePercentage(Integer totalCount, Integer rating) {
		logger.info("calculatePercentage method - calculating the percentage for employee skill rating");
		return ((Double.valueOf(rating) * 100) / totalCount);
	}

	/**
	 * Convert the values skill entity values to skill dto object.
	 * 
	 * @param listData
	 * @param totalCount
	 * @param type       activity type of the employee activities.
	 * @return skillDto object of the skill set.
	 */
	private SkillDto convertEntityToDto(List<?> listData, Integer totalCount, String type) {
		SkillDto skillDto = new SkillDto();
		skillDto.setSkillName(type);
		Double percentage = calculateOverallPercentage(totalCount, listData.size());
		skillDto.setPercentage(percentage);
		return skillDto;
	}

	/**
	 * Calculate the overall percentage of the employee activities.
	 * 
	 * @param totalCount
	 * @param sectionCount
	 * @return calculated the percentage value of the activity.
	 */
	private Double calculateOverallPercentage(Integer totalCount, Integer sectionCount) {
		logger.info("calculatePercentage method - calculating the percentage for employee skill rating");
		return ((Double.valueOf(sectionCount) * 100) / totalCount);

	}

}
