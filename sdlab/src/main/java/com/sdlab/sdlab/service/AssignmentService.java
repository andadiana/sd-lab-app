package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Assignment;

import java.util.List;

public interface AssignmentService {

    public List<Assignment> getAllAssignments();
    public Assignment getAssignmentById(int id);
    public Assignment createAssignment(Assignment assignment);
    public Assignment updateAssignment(Assignment assignment);
    public void deleteAssignment(int id);
}
