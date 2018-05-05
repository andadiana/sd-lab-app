package controller;

import client.AssignmentClient;
import client.ClientProvider;
import client.LaboratoryClient;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.AssignmentResponseDTO;
import dto.response.LaboratoryResponseDTO;
import dto.response.SubmissionResponseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Assignment;
import model.Laboratory;
import model.Submission;
import org.modelmapper.ModelMapper;

import java.sql.Date;
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


    private AssignmentClient assignmentClient;

    public void initData(ClientProvider clientProvider) {
        assignmentClient = clientProvider.getAssignmentClient();

        initializeAssignmentsTable();
    }


    private void initializeAssignmentsTable() {
        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));

        List<Assignment> assignments = getAllAssignments();
        if (assignments != null) {
            ObservableList<Assignment> assignmentsObs = FXCollections.observableArrayList(assignments);
            assignmentsTable.setItems(assignmentsObs);
        }
    }

    private List<Assignment> getAllAssignments() {
        return assignmentClient.getAssignments();
    }
}
