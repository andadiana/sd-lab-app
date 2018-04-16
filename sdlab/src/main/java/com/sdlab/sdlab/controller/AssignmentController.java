package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Assignment;
import com.sdlab.sdlab.service.AssignmentService;
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

    @RequestMapping(method = GET)
    public List<Assignment> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        for (Assignment assignment: assignments) {
            System.out.println(assignment);
        }
        return assignments;
    }

    @RequestMapping(method = GET, value = "/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Integer assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);

        //TODO should return resource not found if there is no assignment with that id
        return assignment;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
        //TODO check if lab is valid (add method in studentservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        //TODO create lab for assignment
        assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = PUT, value = "/{assignmentId}")
    public ResponseEntity<String> updateAssignment(@RequestBody Assignment assignment) {
        //TODO check if lab is valid (add method in studentservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        assignmentService.updateAssignment(assignment);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Integer assignmentId) {

        // true -> can delete
        // false -> cannot delete, f.e. is FK reference somewhere
        boolean wasOk = assignmentService.deleteAssignment(assignmentId);

        //TODO do stuff if !ok
//        if (!wasOk) {
//            // will write to user which item couldn't be deleted
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            model.addAttribute("item", item);
//            return "items/error";
//        }
//
//        return "redirect:/items";

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
