package controller;

import client.AttendanceClient;
import client.ClientProvider;
import client.LaboratoryClient;
import client.StudentClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;


public class AttendanceViewAdmin {

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<Attendance, Integer> labNumberColumn;

    @FXML
    private TableColumn<Attendance, String> studentNameColumn;

    @FXML
    private TableColumn<Attendance, Boolean> attendedColumn;

    @FXML
    private ComboBox<Laboratory> labComboBox;

    @FXML
    private ComboBox<Student> studentComboBox;

    @FXML
    private CheckBox attendedCheckBox;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label errorLabel;

    private AttendanceClient attendanceClient;
    private LaboratoryClient labClient;
    private StudentClient studentClient;

    private ObservableList<Attendance> attendanceObs;
    private ObservableList<Laboratory> labsObs;
    private ObservableList<Student> studentsObs;


    public void initData(ClientProvider clientProvider) {
        attendanceClient = clientProvider.getAttendanceClient();
        labClient = clientProvider.getLaboratoryClient();
        studentClient = clientProvider.getStudentClient();

        initializeAttendanceTable();
        initializeLabComboBox();
        initializeStudentComboBox();

        resetError();
    }

    public void updateData() {
        updateStudentComboBox();
        updateLabComboBox();
        updateTableContents();
    }

    private void resetError() {
        errorLabel.setText("");
    }

    private void updateTableContents() {
        List<Attendance> attendance = attendanceClient.getAttendance();
        if (attendance != null) {
             attendanceObs = FXCollections.observableArrayList(attendance);
            attendanceTable.setItems(attendanceObs);
        }
    }

    private void updateLabComboBox() {
        List<Laboratory> laboratories = labClient.getLaboratories();
        if (laboratories != null) {
            labsObs = FXCollections.observableArrayList(laboratories);
            labComboBox.setItems(labsObs);
        }
    }

    private void updateStudentComboBox() {
        List<Student> students = studentClient.getStudents();
        if (students != null) {
            studentsObs = FXCollections.observableArrayList(students);
            studentComboBox.setItems(studentsObs);
        }
    }

    private void initializeLabComboBox() {
        updateLabComboBox();

        labComboBox.setConverter(new StringConverter<Laboratory>() {
            @Override
            public String toString(Laboratory laboratory) {
                return "Lab number " + laboratory.getLabNumber();
            }

            @Override
            public Laboratory fromString(String s) {
                return null;
            }
        });
    }

    private void initializeStudentComboBox() {
        updateStudentComboBox();

        studentComboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                return student.getName() + ", group " + student.getGroup();
            }

            @Override
            public Student fromString(String s) {
                return null;
            }
        });
    }

    private void initializeAttendanceTable() {
        attendedColumn.setCellValueFactory(new PropertyValueFactory<>("attended"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));

        updateTableContents();

        // deselect row when clicked a second time
        attendanceTable.setRowFactory(c -> {
            final TableRow<Attendance> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < attendanceTable.getItems().size() && attendanceTable.getSelectionModel().isSelected(index)  ) {
                    attendanceTable.getSelectionModel().clearSelection();
//                    resetFields();
                    e.consume();
                }
            });
            return row;
        });
    }

    @FXML
    private void attendanceTableClicked (MouseEvent event) {
        Attendance attendance = attendanceTable.getSelectionModel().getSelectedItem();
        if (attendance != null) {
            attendedCheckBox.setSelected(attendance.isAttended());
            Student selectedStudent = studentsObs.stream()
                    .filter(s -> s.getId() == attendance.getStudent().getId()).findFirst().get();
            studentComboBox.setValue(selectedStudent);
        }
    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        try {
            Attendance attendance = parseAttendanceFields();
            attendanceClient.createAttendance(attendance);
            attendanceObs.add(attendance);
            resetError();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        Attendance selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
        if (selectedAttendance == null) {
            errorLabel.setText("Must first select attendance from the table!");
        }
        else {
            try {
                attendanceObs.remove(selectedAttendance);
                Attendance attendance = parseAttendanceFields();
                attendance.setId(selectedAttendance.getId());
                attendanceClient.updateAttendance(attendance);
                attendanceObs.add(attendance);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        Attendance selectedAttendance = attendanceTable.getSelectionModel().getSelectedItem();
        if (selectedAttendance == null) {
            errorLabel.setText("Must first select attendance from the table!");
        }
        else {
            attendanceClient.deleteAttendance(selectedAttendance.getId());
            attendanceObs.remove(selectedAttendance);
        }
    }

    private Attendance parseAttendanceFields() throws Exception {
        if (labComboBox.getValue() == null ||
                studentComboBox.getValue() == null) {
            throw new Exception("All fields must be selected (lab and student!");
        }
        Attendance attendance = new Attendance();
        attendance.setStudent(studentComboBox.getValue());
        attendance.setAttended(attendedCheckBox.isSelected());
        attendance.setLaboratory(labComboBox.getValue());

        return attendance;
    }

}
