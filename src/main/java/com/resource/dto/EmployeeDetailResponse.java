package com.resource.dto;

import java.util.List;

import com.resource.entity.EmployeeCourse;
import com.resource.entity.EmployeeInterview;
import com.resource.entity.EmployeeProject;
import com.resource.entity.EmployeeSkill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDetailResponse {
	private List<EmployeeSkill> employeeSkillList;
	private List<EmployeeProject> employeeProjectList;
	private List<EmployeeInterview> employeeInterviewList;
	private List<EmployeeCourse> employeeCourseList;
}
