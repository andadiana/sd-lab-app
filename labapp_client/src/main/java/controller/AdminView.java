package controller;

import client.ClientProvider;
import client.ClientProviderImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import connection.HTTPMethod;
import connection.HTTPRequest;
import injector.GuiceFXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import model.Attendance;
import org.modelmapper.ModelMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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

    private Parent labsViewAdmin;

    private Parent studentsViewAdmin;
    private Parent submissionsViewAdmin;
    private Parent assignmentsViewAdmin;
    private Parent attendanceViewAdmin;

    private ClientProvider clientProvider;

    @FXML
    private void initialize() {

        clientProvider = new ClientProviderImpl();
    }

    @FXML
    private void studentsButtonClicked(ActionEvent event) throws IOException{
        if (studentsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentsViewAdmin.fxml"));
            studentsViewAdmin = loader.load();

            StudentsViewAdmin controller = loader.<StudentsViewAdmin>getController();
            controller.initData(clientProvider);
        }

        clearCenterPane();
        centerPane.getChildren().add(studentsViewAdmin);
    }

    @FXML
    public void laboratoriesButtonClicked(ActionEvent event) throws IOException {
        if (labsViewAdmin == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LabsViewAdmin.fxml"));
            labsViewAdmin = loader.load();


            LabsViewAdmin controller = loader.getController();
            controller.initData(clientProvider);
        }

        clearCenterPane();
        centerPane.getChildren().add(labsViewAdmin);
    }

    @FXML
    public void assignmentsButtonClicked(ActionEvent event) throws IOException{
        if (assignmentsViewAdmin == null) {
            URL url = this.getClass().getResource("/AssignmentsViewAdmin.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            assignmentsViewAdmin = loader.load();

            AssignmentsViewAdmin controller = loader.<AssignmentsViewAdmin>getController();
            controller.initData(clientProvider);
        }

        clearCenterPane();
        centerPane.getChildren().add(assignmentsViewAdmin);
    }

    @FXML
    public void attendanceButtonClicked(ActionEvent event) throws IOException{
        if (attendanceViewAdmin == null) {
            URL url = this.getClass().getResource("/AttendanceViewAdmin.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            attendanceViewAdmin = loader.load();

            AttendanceViewAdmin controller = loader.<AttendanceViewAdmin>getController();
            controller.initData(clientProvider);
        }

        clearCenterPane();
        centerPane.getChildren().add(attendanceViewAdmin);
    }

    @FXML
    public void submissionsButtonClicked(ActionEvent event) throws IOException{
        if (submissionsViewAdmin == null) {
            URL url = this.getClass().getResource("/SubmissionsViewAdmin.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            submissionsViewAdmin = loader.load();

            SubmissionsViewAdmin controller = loader.<SubmissionsViewAdmin>getController();
            controller.initData(clientProvider);
        }

        clearCenterPane();
        centerPane.getChildren().add(submissionsViewAdmin);
    }

    private void clearCenterPane() {
        centerPane.getChildren().clear();
    }
}
