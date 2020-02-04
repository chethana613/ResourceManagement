package com.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	private String role;
	private Long employeeId;
	private String message;
	private Integer statusCode;

}
