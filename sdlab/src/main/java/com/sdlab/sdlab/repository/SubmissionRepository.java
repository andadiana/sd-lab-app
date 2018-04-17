package com.sdlab.sdlab.repository;

import com.sdlab.sdlab.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
}
