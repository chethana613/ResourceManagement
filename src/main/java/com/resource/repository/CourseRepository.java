package com.resource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.entity.Course;
import com.resource.entity.Skill;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	/**
	 * @author Sri Keerthna.
	 * @since 2020-01-30.
	 * This method will filter the skills based on skill id.
	 * @param skill  - Skill detail
	 * @return list of courses that are under certain skills.
	 */
	List<Course> findBySkill(Skill skill);

}
