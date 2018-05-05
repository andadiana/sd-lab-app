package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

public class ClientProviderImpl implements ClientProvider {

    private LaboratoryClient laboratoryClient;
    private StudentClient studentClient;
    private AssignmentClient assignmentClient;
    private AttendanceClient attendanceClient;
    private SubmissionClient submissionClient;

    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    public ClientProviderImpl() {
        this.objectMapper = new ObjectMapper();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public LaboratoryClient getLaboratoryClient() {
        if (laboratoryClient == null) {
            this.laboratoryClient = new LaboratoryClientImpl(objectMapper, modelMapper);
        }
        return laboratoryClient;
    }

    @Override
    public StudentClient getStudentClient() {
        if (studentClient == null) {
            this.studentClient = new StudentClientImpl(objectMapper, modelMapper);
        }
        return studentClient;
    }

    @Override
    public AssignmentClient getAssignmentClient() {
        if (assignmentClient == null) {
            this.assignmentClient = new AssignmentClientImpl(objectMapper, modelMapper);
        }
        return assignmentClient;
    }

    @Override
    public AttendanceClient getAttendanceClient() {
        if (attendanceClient == null) {
            this.attendanceClient = new AttendanceClientImpl(objectMapper, modelMapper);
        }
        return attendanceClient;
    }

    @Override
    public SubmissionClient getSubmissionClient() {
        if (submissionClient == null) {
            this.submissionClient = new SubmissionClientImpl(objectMapper, modelMapper);
        }
        return submissionClient;
    }
}
