package com.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.entity.Skill;
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer>{

}
