package controller;

import client.AttendanceClient;
import client.ClientProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Attendance;

import java.util.List;


public class AttendanceViewAdmin {

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<Attendance, Integer> labNumberColumn;

    @FXML
    private TableColumn<Attendance, String> studentNameColumn;

    @FXML
    private TableColumn<Attendance, Boolean> attendedColumn;

    private AttendanceClient attendanceClient;


    public void initData(ClientProvider clientProvider) {
        attendanceClient = clientProvider.getAttendanceClient();

        initializeAttendanceTable();
    }

    private void initializeAttendanceTable() {
        attendedColumn.setCellValueFactory(new PropertyValueFactory<>("attended"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));

        List<Attendance> attendance = getAllAttendance();
        if (attendance != null) {
            ObservableList<Attendance> attendanceObs = FXCollections.observableArrayList(attendance);
//            System.out.println("\n\n" + attendanceObs.size());
            attendanceTable.setItems(attendanceObs);
        }
    }

    private List<Attendance> getAllAttendance() {
        return attendanceClient.getAttendance();
    }
}
