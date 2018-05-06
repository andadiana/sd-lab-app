package controller;

import client.ClientProvider;
import client.ClientProviderImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Attendance;
import model.UserCredentials;

import java.io.IOException;
import java.net.URL;

public class AdminView {

    @FXML
    private Pane centerPane;

    @FXML
    private Button studentsButton;

    @FXML
    private Button laboratoriesButton;

    @FXML
    private Button assignmentsButton;

    @FXML
    private Button attendanceButton;

    @FXML
    private Button submissionsButton;

    @FXML
    private Button logoutButton;

    private Parent labsViewAdmin;
    private Parent studentsViewAdmin;
    private Parent submissionsViewAdmin;
    private Parent assignmentsViewAdmin;
    private Parent attendanceViewAdmin;

    private LabsViewAdmin labsViewAdminController;
    private StudentsViewAdmin studentsViewAdminController;
    private SubmissionsViewAdmin submissionsViewAdminController;
    private AttendanceViewAdmin attendanceViewAdminController;
    private AssignmentsViewAdmin assignmentsViewAdminController;

    private ClientProvider clientProvider;

    private UserCredentials userCredentials;

//    @FXML
//    private void initialize() {
//
//        clientProvider = new ClientProviderImpl();
//    }

    public void initData(ClientProvider clientProvider, UserCredentials userCredentials) {
        this.clientProvider = clientProvider;
        this.userCredentials = userCredentials;
    }

    @FXML
    private void studentsButtonClicked(ActionEvent event) throws IOException{
        if (studentsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentsViewAdmin.fxml"));
            studentsViewAdmin = loader.load();

            studentsViewAdminController = loader.getController();
            studentsViewAdminController.initData(clientProvider);
        }
        else {
            studentsViewAdminController.updateTableContents();
        }

        clearCenterPane();
        centerPane.getChildren().add(studentsViewAdmin);
    }

    @FXML
    public void laboratoriesButtonClicked(ActionEvent event) throws IOException {

        if (labsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LabsViewAdmin.fxml"));
            labsViewAdmin = loader.load();

            labsViewAdminController = loader.getController();
            labsViewAdminController.initData(clientProvider);
        }
        else {
            labsViewAdminController.updateTableContents();
        }

        clearCenterPane();
        centerPane.getChildren().add(labsViewAdmin);

    }

    @FXML
    public void assignmentsButtonClicked(ActionEvent event) throws IOException{
        if (assignmentsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AssignmentsViewAdmin.fxml"));
            assignmentsViewAdmin = loader.load();

            assignmentsViewAdminController = loader.getController();
            assignmentsViewAdminController.initData(clientProvider);
        }
        else {
            assignmentsViewAdminController.updateData();
        }

        clearCenterPane();
        centerPane.getChildren().add(assignmentsViewAdmin);
    }

    @FXML
    public void attendanceButtonClicked(ActionEvent event) throws IOException{
        if (attendanceViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AttendanceViewAdmin.fxml"));
            attendanceViewAdmin = loader.load();

            attendanceViewAdminController = loader.<AttendanceViewAdmin>getController();
            attendanceViewAdminController.initData(clientProvider);
        }
        else {
            attendanceViewAdminController.updateData();
        }

        clearCenterPane();
        centerPane.getChildren().add(attendanceViewAdmin);
    }

    @FXML
    public void submissionsButtonClicked(ActionEvent event) throws IOException{
        if (submissionsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SubmissionsViewAdmin.fxml"));
            submissionsViewAdmin = loader.load();

            submissionsViewAdminController = loader.<SubmissionsViewAdmin>getController();
            submissionsViewAdminController.initData(clientProvider);
        }
        else {
            submissionsViewAdminController.updateData();
        }

        clearCenterPane();
        centerPane.getChildren().add(submissionsViewAdmin);
    }

    @FXML
    public void logoutButtonClicked(ActionEvent event) throws IOException {
        Stage stage1 = (Stage) logoutButton.getScene().getWindow();
        stage1.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Lab Application");
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    private void clearCenterPane() {
        centerPane.getChildren().clear();
    }
}
