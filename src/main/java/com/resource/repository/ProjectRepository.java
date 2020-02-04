package com.resource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.entity.Project;
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>{

}
