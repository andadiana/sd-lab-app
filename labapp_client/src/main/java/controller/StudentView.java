package controller;

import client.ClientProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Student;
import model.UserCredentials;

import java.io.IOException;

public class StudentView {


    @FXML
    private Pane centerPane;

    @FXML
    private Button laboratoriesButton;

    @FXML
    private Button assignmentsButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Button logoutButton;

    private Parent labsViewStudent;
    private Parent submissionsViewStudent;
    private Parent assignmentsViewStudent;

    private LabsViewStudent labsViewStudentController;
    private SubmissionsViewStudent submissionsViewStudentController;
    private AssignmentsViewStudent assignmentsViewStudentController;

    private ClientProvider clientProvider;

    private UserCredentials userCredentials;

    private Student currentStudent;

    public void initData(ClientProvider clientProvider, UserCredentials userCredentials, Student student) {
        this.clientProvider = clientProvider;
        this.userCredentials = userCredentials;
        this.currentStudent = student;

        nameLabel.setText("Welcome, " + currentStudent.getName() + "!");
    }

    @FXML
    public void laboratoriesButtonClicked(ActionEvent event) throws IOException {

        if (labsViewStudent == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LabsViewStudent.fxml"));
            labsViewStudent = loader.load();

            labsViewStudentController = loader.getController();
            labsViewStudentController.initData(clientProvider, userCredentials);
        }
        else {
            labsViewStudentController.updateTableContents();
        }

        clearCenterPane();
        centerPane.getChildren().add(labsViewStudent);

    }

    @FXML
    public void assignmentsButtonClicked(ActionEvent event) throws IOException{

        if (assignmentsViewStudent == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssignmentsViewStudent.fxml"));
            assignmentsViewStudent = loader.load();

            assignmentsViewStudentController = loader.getController();
            assignmentsViewStudentController.initData(clientProvider, userCredentials);
        }
        else {
            assignmentsViewStudentController.updateTableContents();
        }

        clearCenterPane();
        centerPane.getChildren().add(assignmentsViewStudent);
    }

    @FXML
    public void submissionsButtonClicked(ActionEvent event) throws IOException{
        if (submissionsViewStudent == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubmissionsViewStudent.fxml"));
            submissionsViewStudent = loader.load();

            submissionsViewStudentController = loader.getController();
            submissionsViewStudentController.initData(clientProvider, currentStudent, userCredentials);
        }
        else {
            submissionsViewStudentController.updateData();
        }

        clearCenterPane();
        centerPane.getChildren().add(submissionsViewStudent);
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) logoutButton.getScene().getWindow();
        stage1.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Lab Application");
        stage.setScene(new Scene(root, 380, 280));
        stage.show();
    }

    private void clearCenterPane() {
        centerPane.getChildren().clear();
    }
}
