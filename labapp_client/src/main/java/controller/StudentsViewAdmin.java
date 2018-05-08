package controller;

import client.ClientProvider;
import client.StudentClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Student;
import model.UserCredentials;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentsViewAdmin {

    @FXML
    private TableView<Student> studentsTable;


    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, String> groupColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField groupTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label tokenLabel;

    @FXML
    private TextField tokenTextField;

    private StudentClient studentClient;
    private ObservableList<Student> studentsObs;

    private UserCredentials userCredentials;


    public void initData(ClientProvider clientProvider, UserCredentials userCredentials) {
        studentClient = clientProvider.getStudentClient();
        this.userCredentials = userCredentials;

        initializeStudentsTable();
        nameTextField.setPromptText("Student name");
        emailTextField.setPromptText("Student email");
        groupTextField.setPromptText("Student group (30---)");
        tokenLabel.setVisible(false);
        tokenTextField.setVisible(false);
        resetError();
    }

    public void updateTableContents() {
        try {
            List<Student> students = studentClient.getStudents(userCredentials);
            if (students != null) {
                studentsObs = FXCollections.observableArrayList(students);
                studentsTable.setItems(studentsObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void initializeStudentsTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));

        updateTableContents();

        // deselect row when clicked a second time
        studentsTable.setRowFactory(c -> {
            final TableRow<Student> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < studentsTable.getItems().size() && studentsTable.getSelectionModel().isSelected(index)  ) {
                    studentsTable.getSelectionModel().clearSelection();
                    resetFields();
                    resetTokenFields();
                    resetError();
                    e.consume();
                }
            });
            return row;
        });
    }

    @FXML
    private void studentsTableClicked(MouseEvent event) {
        Student student = studentsTable.getSelectionModel().getSelectedItem();
        if (student != null) {
            nameTextField.setText(student.getName());
            emailTextField.setText(student.getEmail());
            groupTextField.setText(student.getGroup());
            resetTokenFields();

        }
    }

    private void resetFields() {
        nameTextField.clear();
        emailTextField.clear();
        groupTextField.clear();
    }

    private void resetTokenFields() {
        tokenLabel.setVisible(false);
        tokenTextField.setVisible(false);
    }

    private void resetError() {
        errorLabel.setText("");
    }

    private boolean existingStudentEmail(Student student) {
        long n = studentsObs.stream().filter(s -> s.getEmail().equals(student.getEmail())).count();
        if (n == 0) {
            return false;
        }
        return true;
    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        try {
            Student student = parseStudentFields();
            if (existingStudentEmail(student)) {
                errorLabel.setText("Email already exists!");
            }
            else {
                String token = studentClient.createStudent(student, userCredentials);
                if (token == null) {
                    errorLabel.setText("Unsuccessful create operation!");
                } else {
                    studentsObs.add(student);
                    tokenLabel.setVisible(true);
                    tokenTextField.setVisible(true);
                    tokenLabel.setText("Token created for student " + student.getName() + " is:");
                    tokenTextField.setText(token);
                    updateTableContents();
                }
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {

        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            errorLabel.setText("Must first select a student from the table!");
        }
        else {
            try {

                Student student = parseStudentFields();
                studentsObs.remove(selectedStudent);
                student.setId(selectedStudent.getId());
                studentClient.updateStudent(student, userCredentials);
                studentsObs.add(student);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        Student selectedStudent = studentsTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            errorLabel.setText("Must first select a student from the table!");
        }
        else {
            try {
                studentClient.deleteStudent(selectedStudent.getId(), userCredentials);
                studentsObs.remove(selectedStudent);
                resetFields();
                resetError();
                resetTokenFields();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    private Student parseStudentFields() throws Exception {
        if (nameTextField.getText().trim().equals("") ||
                emailTextField.getText().trim().equals("") ||
                groupTextField.getText().trim().equals("")) {
            throw new Exception("All fields must be filled!");
        }
        if (!validateGroupFormat(groupTextField.getText())) {
            throw new Exception("Incorrect group format! Must be 30---");
        }
        if (!validUsernameFormat(emailTextField.getText())) {
            throw new Exception("Invalid email format!");
        }
        Student student = new Student();
        student.setEmail(emailTextField.getText());
        student.setName(nameTextField.getText());
        student.setGroup(groupTextField.getText());
        System.out.println(student);
        return student;
    }

    private boolean validateGroupFormat(String group) {
        String GROUP_PATTERN = "^[0-9]{5}$";
        Pattern pattern = Pattern.compile(GROUP_PATTERN);
        Matcher matcher = pattern.matcher(group);
        return matcher.matches();
    }

    private boolean validUsernameFormat(String username) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
