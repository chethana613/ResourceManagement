package com.resource.service;

import java.util.List;

import com.resource.dto.SkillDto;
import com.resource.exception.InvalidAnalyticTypeException;

public interface EmployeeAnalyticGraphService {

	public List<SkillDto> getAnalyticDetailsByEmployeeId(String chartType, Long employeeId)
			throws InvalidAnalyticTypeException;
}
