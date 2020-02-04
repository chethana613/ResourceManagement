package com.resource.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeCourseId;
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;
	private Double markObtained;
	private LocalDate startDate;
	private LocalDate endDate;
	
	

}
