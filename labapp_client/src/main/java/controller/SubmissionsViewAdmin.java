package controller;

import client.ClientProvider;
import client.SubmissionClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.StudentResponseDTO;
import dto.response.SubmissionResponseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Submission;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SubmissionsViewAdmin {

    @FXML
    private TableView<Submission> submissionsTable;

    @FXML
    private TableColumn<Submission, Integer> assignmentNameColumn;

    @FXML
    private TableColumn<Submission, Integer> studentNameColumn;

    @FXML
    private TableColumn<Submission, Date> dateColumn;

    @FXML
    private TableColumn<Submission, Integer> gradeColumn;

    private SubmissionClient submissionClient;


    public void initData(ClientProvider clientProvider) {
        submissionClient = clientProvider.getSubmissionClient();

        initializeSubmissionsTable();
    }

    private void initializeSubmissionsTable() {
        assignmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("assignmentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        List<Submission> submissions = getAllSubmissions();
        if (submissions != null) {
            ObservableList<Submission> submissionsObs = FXCollections.observableArrayList(submissions);
            submissionsTable.setItems(submissionsObs);
        }
    }

    private List<Submission> getAllSubmissions() {
        return submissionClient.getSubmissions();
    }
}
