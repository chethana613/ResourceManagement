package com.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDto {
	
	private String errorMessage;
	private Integer errorStatusCode;

}
