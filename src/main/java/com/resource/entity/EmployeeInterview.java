package com.resource.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EmployeeInterview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeInterviewId;
	@OneToOne
	@JoinColumn(name = "interviewer_id")
	private Employee employeeInterviewer;
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
	@ManyToOne
	@JoinColumn(name = "interview_id")
	private Interview interview;
	@ManyToOne
	@JoinColumn(name = "skill_id")
	private EmployeeSkill employeeSkill;
	@OneToOne
	@JoinColumn(name = "rating_id")
	private Rating rating;
	private String feedBack;
}
