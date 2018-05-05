package controller;

import client.AttendanceClient;
import client.ClientProvider;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import connection.HTTPRequest;
import dto.response.AssignmentResponseDTO;
import dto.response.AttendanceResponseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Assignment;
import model.Attendance;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AttendanceViewAdmin {

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<Attendance, Integer> labNumberColumn;

    @FXML
    private TableColumn<Attendance, Integer> studentNameColumn;

    @FXML
    private TableColumn<Attendance, Boolean> attendedColumn;

    private AttendanceClient attendanceClient;


    public void initData(ClientProvider clientProvider) {
        attendanceClient = clientProvider.getAttendanceClient();

        initializeAttendanceTable();
    }

    private void initializeAttendanceTable() {
        attendedColumn.setCellValueFactory(new PropertyValueFactory<>("attended"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("laboratoryId"));

        List<Attendance> attendance = getAllAttendance();
        if (attendance != null) {
            ObservableList<Attendance> attendanceObs = FXCollections.observableArrayList(attendance);
            attendanceTable.setItems(attendanceObs);
        }
    }

    private List<Attendance> getAllAttendance() {
        return attendanceClient.getAttendance();
    }
}
