package client;

import model.Assignment;
import model.UserCredentials;

import java.util.List;

public interface AssignmentClient {

    public List<Assignment> getAssignments(UserCredentials userCredentials) throws Exception;
    public Assignment getAssignment(int id, UserCredentials userCredentials) throws Exception;
    public void createAssignment(Assignment assignment, UserCredentials userCredentials) throws Exception;
    public void updateAssignment(Assignment assignment, UserCredentials userCredentials) throws Exception;
    public void deleteAssignment(int id, UserCredentials userCredentials) throws Exception;
}
