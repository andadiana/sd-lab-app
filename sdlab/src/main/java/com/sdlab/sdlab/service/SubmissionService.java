package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Submission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubmissionService {

    public List<Submission> getAllSubmissions();
    public Submission getSubmissionById(int id);
    public Submission createSubmission(Submission submission);
    public Submission updateSubmission(Submission submission);
    public void deleteSubmission(int id);
}
