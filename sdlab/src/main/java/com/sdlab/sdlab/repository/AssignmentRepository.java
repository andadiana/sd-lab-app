package com.sdlab.sdlab.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.sdlab.sdlab.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    public List<Assignment> getAssignmentsByLaboratoryId(int labId);
}
