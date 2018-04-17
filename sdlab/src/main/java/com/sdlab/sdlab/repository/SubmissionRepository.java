package com.sdlab.sdlab.repository;

import com.sdlab.sdlab.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    public List<Submission> getSubmissionsByAssignmentId(int assignmentId);
}
