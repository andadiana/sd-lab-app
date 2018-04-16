package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Assignment;
import com.sdlab.sdlab.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(int id) {
        Optional<Assignment> res = assignmentRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    public Assignment createAssignment(Assignment assignment) {
        System.out.println("Assignment service create: " + assignment);
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(Assignment assignment) {
        Assignment assignmentToUpdate = assignmentRepository.getOne(assignment.getId());
        assignmentToUpdate.setName(assignment.getName());
        assignmentToUpdate.setDeadline(assignment.getDeadline());
        assignmentToUpdate.setDescription(assignment.getDescription());
        //assignmentToUpdate.setLaboratory(assignment.getLaboratory());
        return assignmentRepository.save(assignmentToUpdate);
    }

    public boolean deleteAssignment(int id) {
        Optional<Assignment> assignmentToDelete = assignmentRepository.findById(id);
        //TODO check if assignment exists
        assignmentRepository.delete(assignmentToDelete.get());
        return true;
    }
}
