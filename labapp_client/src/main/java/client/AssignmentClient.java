package client;

import model.Assignment;

import java.util.List;

public interface AssignmentClient {

    public List<Assignment> getAssignments();
    public Assignment getAssignment(int id);
    //TODO boolean return value for create operations? to know when operations are not successful
    public void createAssignment(Assignment assignment);
    public void updateAssignment(Assignment assignment);
    public void deleteAssignment(int id);
}
