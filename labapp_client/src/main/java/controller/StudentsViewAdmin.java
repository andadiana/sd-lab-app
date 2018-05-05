package controller;

import client.ClientProvider;
import client.StudentClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;

import java.util.List;

public class StudentsViewAdmin {

    @FXML
    private TableView<Student> studentsTable;


    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> groupColumn;


    private StudentClient studentClient;


    public void initData(ClientProvider clientProvider) {
        studentClient = clientProvider.getStudentClient();

        initializeStudentsTable();
    }

    private void initializeStudentsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        List<Student> students = getAllStudents();
        if (students != null) {
            ObservableList<Student> studentsObs = FXCollections.observableArrayList(students);
            studentsTable.setItems(studentsObs);
        }
    }

    private List<Student> getAllStudents() {
        return studentClient.getStudents();
    }

}
