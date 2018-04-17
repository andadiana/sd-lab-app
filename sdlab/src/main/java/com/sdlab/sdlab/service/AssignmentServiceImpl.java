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

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment getAssignmentById(int id) {
        Optional<Assignment> res = assignmentRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Assignment createAssignment(Assignment assignment) {
        System.out.println("Assignment service create: " + assignment);
        assignment.setId(0);
        return assignmentRepository.save(assignment);
    }

    @Override
    public Assignment updateAssignment(Assignment assignment) {
        Assignment assignmentToUpdate = assignmentRepository.getOne(assignment.getId());
        if (assignment.getName() != null) {
            assignmentToUpdate.setName(assignment.getName());
        }
        if (assignment.getDeadline() != null) {
            assignmentToUpdate.setDeadline(assignment.getDeadline());
        }
        if (assignment.getDescription() != null) {
            assignmentToUpdate.setDescription(assignment.getDescription());
        }
        if (assignment.getLaboratory() != null) {
            assignmentToUpdate.setLaboratory(assignment.getLaboratory());
        }
        return assignmentRepository.save(assignmentToUpdate);
    }

    @Override
    public void deleteAssignment(int id) {
        Optional<Assignment> res = assignmentRepository.findById(id);
        if (res.isPresent()) {
            assignmentRepository.deleteById(id);
        }
    }
}
