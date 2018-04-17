package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Submission;
import com.sdlab.sdlab.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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
        return submissionRepository.save(submission);
    }

    @Override
    public void deleteSubmission(int id) {
        Optional<Submission> res = submissionRepository.findById(id);
        if (res.isPresent()) {
            submissionRepository.deleteById(id);
        }
    }
}
