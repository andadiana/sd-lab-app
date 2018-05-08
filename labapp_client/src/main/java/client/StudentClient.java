package client;

import dto.request.PasswordUpdateDTO;
import dto.response.StudentCreationResponseDTO;
import model.Student;
import model.UserCredentials;

import java.util.List;

public interface StudentClient {

    public List<Student> getStudents(UserCredentials userCredentials) throws Exception;
    public Student getStudent(int id, UserCredentials userCredentials) throws Exception;
    public String createStudent(Student student, UserCredentials userCredentials) throws Exception;
    public void updateStudent(Student student, UserCredentials userCredentials) throws Exception;
    public void deleteStudent(int id, UserCredentials userCredentials) throws Exception;
    public boolean updatePassword(Student student, PasswordUpdateDTO passwordUpdateDTO, UserCredentials userCredentials) throws Exception;

}
