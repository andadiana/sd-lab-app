package controller;

import client.AttendanceClient;
import client.ClientProvider;
import client.LaboratoryClient;
import client.StudentClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import model.*;

import java.util.List;
import java.util.stream.Collectors;


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

    private UserCredentials userCredentials;

    public void initData(ClientProvider clientProvider, UserCredentials userCredentials) {
        attendanceClient = clientProvider.getAttendanceClient();
        labClient = clientProvider.getLaboratoryClient();
        studentClient = clientProvider.getStudentClient();

        this.userCredentials = userCredentials;

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
        try {
            List<Attendance> attendance = attendanceClient.getAttendance(userCredentials);
            if (attendance != null) {
                attendanceObs = FXCollections.observableArrayList(attendance);
                attendanceTable.setItems(attendanceObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void updateTableContents(Laboratory lab) {
        try {
            List<Attendance> attendance = attendanceClient.getAttendance(userCredentials);
            if (attendance != null) {
                List<Attendance> attendanceFiltered = attendance.stream()
                        .filter(a -> a.getLaboratory().getId() == lab.getId()).collect(Collectors.toList());
                attendanceObs = FXCollections.observableArrayList(attendanceFiltered);
                attendanceTable.setItems(attendanceObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void updateLabComboBox() {
        try {
            List<Laboratory> laboratories = labClient.getLaboratories(userCredentials);
            if (laboratories != null) {
                labsObs = FXCollections.observableArrayList(laboratories);
                labComboBox.setItems(labsObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void updateStudentComboBox() {
        try {
            List<Student> students = studentClient.getStudents(userCredentials);
            if (students != null) {
                studentsObs = FXCollections.observableArrayList(students);
                studentComboBox.setItems(studentsObs);
                resetError();
            }
        }catch (Exception e) {
            errorLabel.setText(e.getMessage());
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
    private void labComboBoxClicked(ActionEvent event) {
        Laboratory lab = labComboBox.getSelectionModel().getSelectedItem();
        if (lab != null) {
            updateTableContents(lab);
        }
    }

    @FXML
    private void attendanceTableClicked (MouseEvent event) {
        Attendance attendance = attendanceTable.getSelectionModel().getSelectedItem();
        if (attendance != null) {
            attendedCheckBox.setSelected(attendance.isAttended());
            Student selectedStudent = studentsObs.stream()
                    .filter(s -> s.getId() == attendance.getStudent().getId()).findFirst().get();
            Laboratory selectedLab = labsObs.stream()
                    .filter(l -> l.getId() == attendance.getLaboratory().getId()).findFirst().get();
            studentComboBox.setValue(selectedStudent);
            labComboBox.setValue(selectedLab);
        }
    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        try {
            Attendance attendance = parseAttendanceFields();
            long n = attendanceObs.stream()
                    .filter(a -> a.getLaboratory().getId() == labComboBox.getValue().getId() &&
                            a.getStudent().getId() == studentComboBox.getValue().getId()).count();
            if (n != 0) {
                errorLabel.setText("There is already an attendance entry for this lab for the selected student!");
            }
            else {
                attendanceClient.createAttendance(attendance, userCredentials);
//            attendanceObs.add(attendance);
                updateTableContents(labComboBox.getValue());
                resetError();
            }
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

                Attendance attendance = parseAttendanceFields();
                attendanceObs.remove(selectedAttendance);
                attendance.setId(selectedAttendance.getId());
                attendanceClient.updateAttendance(attendance, userCredentials);
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
            try {
                attendanceClient.deleteAttendance(selectedAttendance.getId(), userCredentials);
                attendanceObs.remove(selectedAttendance);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
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
