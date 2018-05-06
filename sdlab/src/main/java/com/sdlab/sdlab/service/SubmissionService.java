package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Submission;

import java.util.List;

public interface SubmissionService {

    public List<Submission> getAllSubmissions();
    public Submission getSubmissionById(int id);
    public Submission createSubmission(Submission submission);
    public Submission updateSubmission(Submission submission);
    public void deleteSubmission(int id);
    public List<Submission> getSubmissionsByAssignmmentId(int assignmentId);
    public List<Submission> getSubmissionsByStudentId(int studentId);
    public boolean submissionExists(int studentId, int assignmentId);
    public boolean validSubmission(Submission submission);
}
