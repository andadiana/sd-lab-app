package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Assignment;
import com.sdlab.sdlab.model.Submission;
import com.sdlab.sdlab.repository.AssignmentRepository;
import com.sdlab.sdlab.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubmissionServiceImpl implements SubmissionService{

    public static final int MAX_NR_SUBMISSIONS = 1;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

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
        Date currentDate = new Date();
        Assignment assignment = assignmentRepository.getOne(submission.getAssignment().getId());
        if (currentDate.after(assignment.getDeadline())) {
            return null;
        }
        submission.setDate(new Date()); //set date to current date
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

    @Override
    public List<Submission> getSubmissionsByAssignmmentId(int assignmentId) {
        return submissionRepository.getSubmissionsByAssignmentId(assignmentId);
    }

    @Override
    public boolean submissionExists(int studentId, int assignmentId) {
        List<Submission> submissions = getSubmissionsByStudentIdAndAssignmentId(studentId, assignmentId);
        if (submissions.size() >= MAX_NR_SUBMISSIONS) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validSubmission(Submission submission) {
        Date currentDate = new Date();
        Assignment assignment = assignmentRepository.getOne(submission.getAssignment().getId());
        if (assignment.getDeadline().before(currentDate)) {
            return false;
        }
        return true;
    }

    public List<Submission> getSubmissionsByStudentIdAndAssignmentId(int studentId, int assignmentId) {
        return submissionRepository.getSubmissionsByStudentIdAndAssignmentId(studentId, assignmentId);
    }
}
