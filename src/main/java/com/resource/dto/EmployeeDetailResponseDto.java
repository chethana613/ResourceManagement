package com.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDetailResponseDto {
	private Integer statusCode;
	private String message;
	private EmployeeDetailResponse employeeDetailResponse;
}
