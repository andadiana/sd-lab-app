package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Assignment;
import model.Submission;

import java.util.List;

public class GradesView {

    @FXML
    private TableView<Submission> gradesTable;

    @FXML
    private TableColumn<Submission, String> nameColumn;

    @FXML
    private TableColumn<Submission, String> groupColumn;

    @FXML
    private TableColumn<Submission, Integer> gradeColumn;

    @FXML
    private Label assignmentNameLabel;

    public void initData(List<Submission> submissions, Assignment assignment) {
        gradesTable.setSelectionModel(null);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("studentGroup"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        ObservableList<Submission> submissionsObs = FXCollections.observableArrayList(submissions);
        gradesTable.setItems(submissionsObs);

        assignmentNameLabel.setText("Grade for assignment " + assignment.getName());
    }
}
