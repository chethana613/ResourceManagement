package com.resource.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sri Keerthna.
 * @since 2020-01-28. This class is used to display the details of bench
 *        resources.
 */
@Getter
@Setter
public class ResourceListResponseDto {

	private Long employeeId;
	private String employeeName;
	private Integer experience;
	private String mailId;
	private Long phoneNumber;
	private String status;
}
