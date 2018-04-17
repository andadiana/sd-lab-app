package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Assignment;
import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.model.Submission;
import com.sdlab.sdlab.service.AssignmentService;
import com.sdlab.sdlab.service.StudentService;
import com.sdlab.sdlab.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = GET)
    public List<Submission> getAllSubmissions() {
        List<Submission> submissions = submissionService.getAllSubmissions();
        for (Submission s: submissions) {
            System.out.println(s);
        }
        return submissions;
    }

    @RequestMapping(method = GET, value = "/{submissionId}")
    public ResponseEntity getSubmissionById(@PathVariable Integer submissionId) {
        Submission submission = submissionService.getSubmissionById(submissionId);
        if (submission == null) {
            //submission not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(submission);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createSubmission(@RequestBody Submission submission) {
        Student student = studentService.getStudentById(submission.getStudent().getId());
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student does not exist!");
        }
        Assignment assignment = assignmentService.getAssignmentById(submission.getAssignment().getId());
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment does not exist!");
        }
        if (submissionService.submissionExists(student.getId(), assignment.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student has already submitted this assignment!");
        }
        Submission createdSubmission = submissionService.createSubmission(submission);
//        return ResponseEntity.status(HttpStatus.OK).body(createdSubmission);
        if (createdSubmission == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot create submission!");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = PUT, value = "/{submissionId}")
    public ResponseEntity updateSubmission(@PathVariable Integer submissionId, @RequestBody Submission submission) {
        Submission submission1 = submissionService.getSubmissionById(submissionId);
        if (submission1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Submission not found!");
        }
        Student student = studentService.getStudentById(submission.getStudent().getId());
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student does not exist!");
        }
        Assignment assignment = assignmentService.getAssignmentById(submission.getAssignment().getId());
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Assignment does not exist!");
        }
        System.out.println(submission1);
//        if (!submission1.getDate().equals(submission.getDate())) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Date field cannot be changed!");
//        }
        if(submissionService.validSubmission(submission)) {
            submission.setId(submissionId);
            submissionService.updateSubmission(submission);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid submission!");
        }
    }

    @RequestMapping(method = DELETE, value = "/{submissionId}")
    public ResponseEntity deleteSubmission(@PathVariable Integer submissionId) {
        submissionService.deleteSubmission(submissionId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = GET, value = "/assignments/{assignmentId}")
    public ResponseEntity getGradesForAssignment(@PathVariable Integer assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found!");
        }
        List<Submission> submissions = submissionService.getSubmissionsByAssignmmentId(assignmentId);
        return ResponseEntity.status(HttpStatus.OK).body(submissions);
    }
}
