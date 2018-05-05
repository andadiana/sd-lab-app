package client;

public interface ClientProvider {

    public LaboratoryClient getLaboratoryClient();
    public StudentClient getStudentClient();
    public AssignmentClient getAssignmentClient();
    public AttendanceClient getAttendanceClient();
    public SubmissionClient getSubmissionClient();
}
