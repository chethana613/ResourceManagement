package com.resource.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {

	private Integer statusCode;
	private String message;
	private List<SkillDto> data;
}
