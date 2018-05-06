package client;

import model.Assignment;
import model.Student;
import model.Submission;

import java.util.List;

public interface SubmissionClient {

    public List<Submission> getSubmissions();
    public Submission getSubmission(int id);
    //TODO boolean return value for create operations? to know when operations are not successful
    public void createSubmission(Submission submission);
    public void updateSubmission(Submission submission);
    public void deleteSubmission(int id);
    public List<Submission> getSubmissionsForAssignment(Assignment assignment);
    public List<Submission> getSubmissionsForStudent(Student student);
}
