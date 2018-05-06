package client;

import dto.request.PasswordUpdateDTO;
import dto.response.StudentCreationResponseDTO;
import model.Student;

import java.util.List;

public interface StudentClient {

    public List<Student> getStudents();
    public Student getStudent(int id);
    //TODO boolean return value for create operations? to know when operations are not successful
    public String createStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(int id);
    public boolean updatePassword(Student student, PasswordUpdateDTO passwordUpdateDTO);

}
