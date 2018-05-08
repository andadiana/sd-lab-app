package client;

import model.Assignment;
import model.Student;
import model.Submission;
import model.UserCredentials;

import java.util.List;

public interface SubmissionClient {

    public List<Submission> getSubmissions(UserCredentials userCredentials) throws Exception;
    public Submission getSubmission(int id, UserCredentials userCredentials) throws Exception;
    public void createSubmission(Submission submission, UserCredentials userCredentials) throws Exception;
    public void updateSubmission(Submission submission, UserCredentials userCredentials) throws Exception;
    public void deleteSubmission(int id, UserCredentials userCredentials) throws Exception;
    public List<Submission> getSubmissionsForAssignment(Assignment assignment, UserCredentials userCredentials) throws Exception;
    public List<Submission> getSubmissionsForStudent(Student student, UserCredentials userCredentials) throws Exception;
}
