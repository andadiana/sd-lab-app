package controller;

import client.AssignmentClient;
import client.ClientProvider;
import client.LaboratoryClient;
import client.SubmissionClient;
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
import model.Assignment;
import model.Laboratory;
import model.Submission;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AssignmentsViewAdmin {

    @FXML
    private TableView<Assignment> assignmentsTable;

    @FXML
    private TableColumn<Assignment, String> assignmentNameColumn;

    @FXML
    private TableColumn<Assignment, Date> deadlineColumn;

    @FXML
    private TableColumn<Assignment, Integer> labNumberColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<Laboratory> labComboBox;


    private AssignmentClient assignmentClient;
    private LaboratoryClient labClient;
    private SubmissionClient submissionClient;

    private ObservableList<Assignment> assignmentsObs;
    private ObservableList<Laboratory> labsObs;

    public void initData(ClientProvider clientProvider) {
        assignmentClient = clientProvider.getAssignmentClient();
        labClient = clientProvider.getLaboratoryClient();
        submissionClient = clientProvider.getSubmissionClient();

        initializeAssignmentsTable();
        initializeLabComboBox();
        //TODO add ability to clear selection from lab combo box -> all assignments displayed

        resetError();
        resetFields();
    }

    public void updateData() {
        updateComboBox();
        updateTableContents();
    }

    private void updateTableContents() {
        List<Assignment> assignments = assignmentClient.getAssignments();
        if (assignments != null) {
            assignmentsObs = FXCollections.observableArrayList(assignments);
            assignmentsTable.setItems(assignmentsObs);
        }
    }

    private void updateComboBox() {
        List<Laboratory> laboratories = labClient.getLaboratories();
        if (laboratories != null) {
            labsObs = FXCollections.observableArrayList(laboratories);
            labComboBox.setItems(labsObs);
        }
    }

    private void initializeAssignmentsTable() {
        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));

        updateTableContents();

        // deselect row when clicked a second time
        assignmentsTable.setRowFactory(c -> {
            final TableRow<Assignment> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < assignmentsTable.getItems().size() && assignmentsTable.getSelectionModel().isSelected(index)  ) {
                    assignmentsTable.getSelectionModel().clearSelection();
                    resetFields();
                    resetError();
                    e.consume();
                }
            });
            return row;
        });
    }

    private void initializeLabComboBox() {
        updateComboBox();

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

    private void resetError() {
        errorLabel.setText("");
    }

    private void resetFields() {
        nameTextField.clear();
        descriptionTextArea.clear();
        labComboBox.setValue(labsObs.get(0));
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void assignmentsTableClicked(MouseEvent event) {
        Assignment assignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (assignment != null) {
            nameTextField.setText(assignment.getName());
            descriptionTextArea.setText(assignment.getDescription());
            datePicker.setValue(assignment.getDeadline().toLocalDate());

            Laboratory selectedLab = labsObs.stream()
                    .filter(l -> l.getId() == assignment.getLaboratory().getId()).findFirst().get();
            labComboBox.setValue(selectedLab);
        }

    }

    @FXML
    private void addButtonClicked(ActionEvent event) {
        try {
            Assignment assignment = parseAssignmentFields();
            assignmentClient.createAssignment(assignment);
            assignmentsObs.add(assignment);
            resetError();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {
        Assignment selectedAssignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAssignment == null) {
            errorLabel.setText("Must first select an assignment from the table!");
        }
        else {
            try {
                assignmentsObs.remove(selectedAssignment);
                Assignment assignment = parseAssignmentFields();
                assignment.setId(selectedAssignment.getId());
                assignmentClient.updateAssignment(assignment);
                assignmentsObs.add(assignment);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        Assignment selectedAssignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAssignment == null) {
            errorLabel.setText("Must first select an assignment from the table!");
        }
        else {
            assignmentClient.deleteAssignment(selectedAssignment.getId());
            assignmentsObs.remove(selectedAssignment);
        }
    }

    @FXML
    private void gradesButtonClicked(ActionEvent event) throws IOException {
        Assignment selectedAssignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (selectedAssignment == null) {
            errorLabel.setText("Must select assignment from table!");
        }
        else {
            List<Submission> submissions = submissionClient.getSubmissionsForAssignment(selectedAssignment);
            resetError();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GradesView.fxml"));
            Parent root = loader.load();
            GradesView controller = loader.getController();
            controller.initData(submissions, selectedAssignment);

            Stage stage = new Stage();
            stage.setTitle("Grades");
            stage.setScene(new Scene(root, 450, 360));
            stage.show();
        }
    }

    private Assignment parseAssignmentFields() throws Exception {
        if (nameTextField.getText().trim().equals("") ||
                descriptionTextArea.getText().trim().equals("") ||
                datePicker.getValue() == null ||
                labComboBox.getValue() == null) {
            throw new Exception("All fields must be filled!");
        }
        Assignment assignment = new Assignment();
        assignment.setName(nameTextField.getText());
        assignment.setDescription(descriptionTextArea.getText());
        assignment.setDeadline(Date.valueOf(datePicker.getValue()));
        assignment.setLaboratory(labComboBox.getValue());

        return assignment;
    }


}
