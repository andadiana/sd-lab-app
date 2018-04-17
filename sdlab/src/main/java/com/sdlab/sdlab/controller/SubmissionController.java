package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Submission;
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
        Submission createdSubmission = submissionService.createSubmission(submission);
        return ResponseEntity.status(HttpStatus.OK).body(createdSubmission);
    }

    @RequestMapping(method = PUT, value = "/{submissionId}")
    public ResponseEntity updateSubmission(@PathVariable Integer submissionId, @RequestBody Submission submission) {
        Submission submission1 = submissionService.getSubmissionById(submissionId);
        if (submission1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Submission not found!");
        }
        submission.setId(submissionId);
        submissionService.updateSubmission(submission);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{submissionId}")
    public ResponseEntity deleteSubmission(@PathVariable Integer submissionId) {
        submissionService.deleteSubmission(submissionId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
