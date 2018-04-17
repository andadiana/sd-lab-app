package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Submission;
import com.sdlab.sdlab.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionServiceImpl implements SubmissionService{

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Submission getSubmissionById(int id) {
        Optional<Submission> res = submissionRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Submission createSubmission(Submission submission) {
        submission.setId(0);
        return submissionRepository.save(submission);
    }

    @Override
    public Submission updateSubmission(Submission submission) {
        Submission submissionToUpdate = submissionRepository.getOne(submission.getId());
        if (submission.getAssignment() != null) {
            submissionToUpdate.setAssignment(submission.getAssignment());
        }
        if (submission.getDate() != null) {
            submissionToUpdate.setDate(submission.getDate());
        }
        if (submission.getDescription() != null) {
            submissionToUpdate.setDescription(submission.getDescription());
        }
        if (submission.getGrade() != 0) {
            submissionToUpdate.setGrade(submission.getGrade());
        }
        if (submission.getStudent() != null) {
            submissionToUpdate.setStudent(submission.getStudent());
        }
        return submissionRepository.save(submissionToUpdate);
    }

    @Override
    public void deleteSubmission(int id) {
        Optional<Submission> res = submissionRepository.findById(id);
        if (res.isPresent()) {
            submissionRepository.deleteById(id);
        }
    }

    @Override
    public List<Submission> getSubmissionsByAssignmmentId(int assignmentId) {
        return submissionRepository.getSubmissionsByAssignmentId(assignmentId);
    }
}
