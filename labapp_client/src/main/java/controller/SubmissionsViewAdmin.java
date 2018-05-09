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
import model.*;

import java.sql.Date;
import java.util.List;

public class SubmissionsViewAdmin {

    @FXML
    private TableView<Submission> submissionsTable;

    @FXML
    private TableColumn<Submission, String> assignmentNameColumn;

    @FXML
    private TableColumn<Submission, String> studentNameColumn;

    @FXML
    private TableColumn<Submission, Date> dateColumn;

    @FXML
    private TableColumn<Submission, Integer> gradeColumn;

    @FXML
    private ComboBox<Assignment> assignmentComboBox;

    @FXML
    private TextField studentNameTextField;

    @FXML
    private TextField gradeTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button gradeButton;

    @FXML
    private Label errorLabel;

    private SubmissionClient submissionClient;
    private AssignmentClient assignmentClient;
    private StudentClient studentClient;

    private ObservableList<Assignment> assignmentsObs;
    private ObservableList<Submission> submissionsObs;

    private UserCredentials userCredentials;


    public void initData(ClientProvider clientProvider, UserCredentials userCredentials) {
        submissionClient = clientProvider.getSubmissionClient();
        assignmentClient = clientProvider.getAssignmentClient();
        studentClient = clientProvider.getStudentClient();

        this.userCredentials = userCredentials;

        initializeSubmissionsTable();
        initializeAssignmentComboBox();

        studentNameTextField.setEditable(false);
        descriptionTextArea.setEditable(false);
        resetError();
    }

    public void updateData() {
        updateAssignmentComboBox();
        updateTableContents();
//        updateTableContents();
    }

    private void resetFields() {
        studentNameTextField.clear();
        descriptionTextArea.clear();
        gradeTextField.clear();
    }

    private void updateTableContents(Assignment assignment) {
        try {
            List<Submission> submissions = submissionClient.getSubmissionsForAssignment(assignment, userCredentials);
            if (submissions != null) {
                submissionsObs = FXCollections.observableArrayList(submissions);
                submissionsTable.setItems(submissionsObs);
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void updateTableContents() {
        try {
            List<Submission> submissions = submissionClient.getSubmissions(userCredentials);
            if (submissions != null) {
                submissionsObs = FXCollections.observableArrayList(submissions);
                submissionsTable.setItems(submissionsObs);
            }
            resetError();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void initializeSubmissionsTable() {
        Assignment assignment = assignmentComboBox.getSelectionModel().getSelectedItem();
        if (assignment != null) {
            updateTableContents(assignment);
        }
        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentName"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));


        // deselect row when clicked a second time
        submissionsTable.setRowFactory(c -> {
            final TableRow<Submission> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < submissionsTable.getItems().size() && submissionsTable.getSelectionModel().isSelected(index)  ) {
                    submissionsTable.getSelectionModel().clearSelection();
//                    resetFields();
                    e.consume();
                }
            });
            return row;
        });
    }

    private void updateAssignmentComboBox() {
        try {
            List<Assignment> assignments = assignmentClient.getAssignments(userCredentials);
            if (assignments != null) {
                assignmentsObs = FXCollections.observableArrayList(assignments);
                assignmentComboBox.setItems(assignmentsObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
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
                return assignment.getName();
            }

            @Override
            public Assignment fromString(String s) {
                return null;
            }
        });
    }

    @FXML
    private void assignmentComboBoxClicked(ActionEvent event) {
        Assignment assignment = assignmentComboBox.getSelectionModel().getSelectedItem();
        if (assignment != null) {
            updateTableContents(assignment);
        }
    }

    @FXML
    private void submissionsTableClicked(MouseEvent event) {
        Submission submission = submissionsTable.getSelectionModel().getSelectedItem();
        if (submission != null) {
            studentNameTextField.setText(submission.getStudentName());
            descriptionTextArea.setText(submission.getDescription());
            gradeTextField.setText(Integer.toString(submission.getGrade()));
        }

    }

    @FXML
    private void gradeButtonClicked(ActionEvent event) {
        Submission selectedSubmission = submissionsTable.getSelectionModel().getSelectedItem();
        if (selectedSubmission == null) {
            errorLabel.setText("Must first select a submission from the table!");
        }
        else {
            try {
                int grade = Integer.parseInt(gradeTextField.getText());
                Submission submission = new Submission();
                submission.setDate(selectedSubmission.getDate());
                submission.setStudent(selectedSubmission.getStudent());
                submission.setAssignment(selectedSubmission.getAssignment());
//                submission.setGrade(selectedSubmission.getGrade());
                submission.setDescription(selectedSubmission.getDescription());
                submission.setId(selectedSubmission.getId());
                submission.setGrade(grade);
                submissionClient.updateSubmission(submission, userCredentials);
                submissionsObs.remove(selectedSubmission);
                submissionsObs.add(submission);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }


}
