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

public class AssignmentsViewStudent {

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
    private DatePicker datePicker;

    @FXML
    private TextField labTextField;


    private AssignmentClient assignmentClient;

    private ObservableList<Assignment> assignmentsObs;

    public void initData(ClientProvider clientProvider) {
        assignmentClient = clientProvider.getAssignmentClient();

        initializeAssignmentsTable();

        resetFields();
    }

    public void updateTableContents() {
        List<Assignment> assignments = assignmentClient.getAssignments();
        if (assignments != null) {
            assignmentsObs = FXCollections.observableArrayList(assignments);
            assignmentsTable.setItems(assignmentsObs);
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
                    e.consume();
                }
            });
            return row;
        });
    }

    private void resetFields() {
        nameTextField.clear();
        descriptionTextArea.clear();
        datePicker.setValue(LocalDate.now());
        labTextField.clear();
    }

    @FXML
    private void assignmentsTableClicked(MouseEvent event) {
        Assignment assignment = assignmentsTable.getSelectionModel().getSelectedItem();
        if (assignment != null) {
            nameTextField.setText(assignment.getName());
            descriptionTextArea.setText(assignment.getDescription());
            datePicker.setValue(assignment.getDeadline().toLocalDate());
            labTextField.setText(assignment.getLaboratory().getTitle());
        }

    }

}
