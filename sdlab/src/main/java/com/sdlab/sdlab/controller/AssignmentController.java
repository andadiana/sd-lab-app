package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.dto.request.AssignmentRequestDTO;
import com.sdlab.sdlab.dto.response.AssignmentResponseDTO;
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
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private LaboratoryService laboratoryService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = GET)
    public List<AssignmentResponseDTO> getAllAssignments() {
        List<Assignment> assignments = assignmentService.getAllAssignments();
        List<AssignmentResponseDTO> assignmentsDTO = assignments.stream()
                .map(a -> modelMapper.map(a, AssignmentResponseDTO.class)).collect(Collectors.toList());
        return assignmentsDTO;
    }

    @RequestMapping(method = GET, value = "/{assignmentId}")
    public ResponseEntity getAssignmentById(@PathVariable Integer assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment == null) {
            //assignment not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(assignment, AssignmentResponseDTO.class));
    }

    @RequestMapping(method = POST)
    public ResponseEntity createAssignment(@RequestBody AssignmentRequestDTO assignmentDTO) {
        Assignment createdAssignment = assignmentService.createAssignment(modelMapper.map(assignmentDTO, Assignment.class));
        //return ResponseEntity.status(HttpStatus.OK).body(createdAssignment);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @RequestMapping(method = PUT, value = "/{assignmentId}")
    public ResponseEntity updateAssignment(@PathVariable Integer assignmentId, @RequestBody AssignmentRequestDTO assignmentDTO) {
        Assignment assignment1 = assignmentService.getAssignmentById(assignmentId);
        if (assignment1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found!");
        }
        Assignment updatedAssignment = modelMapper.map(assignmentDTO, Assignment.class);
        updatedAssignment.setId(assignmentId);
        System.out.println(updatedAssignment);
        assignmentService.updateAssignment(updatedAssignment);
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
        List<AssignmentResponseDTO> assignmentsDTO = assignments.stream()
                .map(a -> modelMapper.map(a, AssignmentResponseDTO.class)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(assignmentsDTO);
    }
}
