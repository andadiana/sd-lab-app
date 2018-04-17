package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Assignment;
import com.sdlab.sdlab.model.Laboratory;
import com.sdlab.sdlab.service.AssignmentService;
import com.sdlab.sdlab.service.LaboratoryService;
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
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private LaboratoryService laboratoryService;

    @RequestMapping(method = GET)
    public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        for (Assignment assignment: assignments) {
            System.out.println(assignment);
        }
        return assignments;
    }

    @RequestMapping(method = GET, value = "/{assignmentId}")
    public ResponseEntity getAssignmentById(@PathVariable Integer assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment == null) {
            //assignment not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(assignment);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createAssignment(@RequestBody Assignment assignment) {
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.OK).body(createdAssignment);
    }

    @RequestMapping(method = PUT, value = "/{assignmentId}")
    public ResponseEntity updateAssignment(@PathVariable Integer assignmentId, @RequestBody Assignment assignment) {
        Assignment assignment1 = assignmentService.getAssignmentById(assignmentId);
        if (assignment1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found!");
        }
        assignment.setId(assignmentId);
        assignmentService.updateAssignment(assignment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{assignmentId}")
    public ResponseEntity deleteAssignment(@PathVariable Integer assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = GET, value = "/labs/{labId}")
    public ResponseEntity getAssignmentsForLab(@PathVariable Integer labId) {
        Laboratory laboratory = laboratoryService.getLabById(labId);
        if (laboratory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Laboratory not found!");
        }
        List<Assignment> assignments = assignmentService.getAssignmentsByLaboratoryId(labId);
        return ResponseEntity.status(HttpStatus.OK).body(assignments);
    }
}
