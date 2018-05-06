package controller;

import client.AssignmentClient;
import client.ClientProvider;
import client.StudentClient;
import client.SubmissionClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import model.Assignment;
import model.Student;
import model.Submission;

import java.sql.Date;
import java.util.List;

public class SubmissionsViewStudent {

    @FXML
    private TableView<Submission> submissionsTable;

    @FXML
    private TableColumn<Submission, String> assignmentNameColumn;

    @FXML
    private TableColumn<Submission, Date> dateColumn;

    @FXML
    private TableColumn<Submission, Integer> gradeColumn;

    @FXML
    private ComboBox<Assignment> assignmentComboBox;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorLabel;

    private SubmissionClient submissionClient;
    private AssignmentClient assignmentClient;
    private StudentClient studentClient;

    private ObservableList<Assignment> assignmentsObs;
    private ObservableList<Submission> submissionsObs;

    private Student currentStudent;


    public void initData(ClientProvider clientProvider, Student student) {
        submissionClient = clientProvider.getSubmissionClient();
        assignmentClient = clientProvider.getAssignmentClient();
        studentClient = clientProvider.getStudentClient();

        currentStudent = student;

        initializeSubmissionsTable();
        initializeAssignmentComboBox();

        resetError();
    }

    public void updateData() {
        updateAssignmentComboBox();
        updateTableContents();
    }

    private void resetFields() {
        descriptionTextArea.clear();
        assignmentComboBox.setValue(assignmentsObs.get(0));
    }

    private void updateTableContents() {
        List<Submission> submissions = submissionClient.getSubmissionsForStudent(currentStudent);
        if (submissions != null) {
            submissionsObs = FXCollections.observableArrayList(submissions);
            submissionsTable.setItems(submissionsObs);
        }
    }

    private void initializeSubmissionsTable() {
        updateTableContents();

        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        // deselect row when clicked a second time
        submissionsTable.setRowFactory(c -> {
            final TableRow<Submission> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < submissionsTable.getItems().size() && submissionsTable.getSelectionModel().isSelected(index)  ) {
                    submissionsTable.getSelectionModel().clearSelection();
                    resetFields();
                    e.consume();
                }
            });
            return row;
        });
    }

    private void updateAssignmentComboBox() {
        List<Assignment> assignments = assignmentClient.getAssignments();
        if (assignments != null) {
            assignmentsObs = FXCollections.observableArrayList(assignments);
            assignmentComboBox.setItems(assignmentsObs);
        }
    }

    private void resetError() {
        errorLabel.setText("");
    }

    private void initializeAssignmentComboBox() {
        updateAssignmentComboBox();

        assignmentComboBox.setConverter(new StringConverter<Assignment>() {
            @Override
            public String toString(Assignment assignment) {
                return "Assignment: " + assignment.getName();
            }

            @Override
            public Assignment fromString(String s) {
                return null;
            }
        });
    }


    @FXML
    private void submissionsTableClicked(MouseEvent event) {
        Submission submission = submissionsTable.getSelectionModel().getSelectedItem();
        if (submission != null) {
            descriptionTextArea.setText(submission.getDescription());
        }

    }

    @FXML
    private void submitButtonClicked(ActionEvent event) {

        try {
            Submission submission = parseSubmission();
            submissionClient.createSubmission(submission);
            submissionsObs.add(submission);
            resetError();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        Submission selectedSubmission = submissionsTable.getSelectionModel().getSelectedItem();
        if (selectedSubmission == null) {
            errorLabel.setText("Must first select a submission from the table!");
        }
        else {
            try {
                submissionsObs.remove(selectedSubmission);
                Submission submission = parseSubmission();
                submission.setId(selectedSubmission.getId());
                submissionClient.updateSubmission(submission);
                submissionsObs.add(submission);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    private Submission parseSubmission() throws Exception {
        Assignment selectedAssignment = assignmentComboBox.getSelectionModel().getSelectedItem();
        if (descriptionTextArea.getText().trim().equals("")) {
            throw new Exception("Description cannot be empty!");
        }
        Date currentDate = new Date(new java.util.Date().getTime());
        if (currentDate.after(selectedAssignment.getDeadline())) {
            throw new Exception("Cannot submit assignment past the deadline!");
        }
        Submission submission = new Submission();
        submission.setDescription(descriptionTextArea.getText());
        submission.setDate(currentDate);
        submission.setGrade(0);
        submission.setAssignment(selectedAssignment);
        submission.setStudent(currentStudent);

        return submission;
    }
}
