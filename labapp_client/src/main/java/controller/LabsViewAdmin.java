package controller;

import client.ClientProvider;
import client.LaboratoryClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import connection.HTTPRequest;
import dto.response.LaboratoryResponseDTO;
import dto.response.StudentResponseDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Laboratory;
import model.Student;
import org.modelmapper.ModelMapper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


public class LabsViewAdmin {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Laboratory> labsTable;

    @FXML
    private TableColumn<Laboratory, Integer> labNumberColumn;

    @FXML
    private TableColumn<Laboratory, String> titleColumn;

    @FXML
    private TableColumn<Laboratory, Date> dateColumn;

//    private ObjectMapper jsonMapper;

//    private ModelMapper modelMapper;


    private LaboratoryClient laboratoryClient;


    public void initData(ClientProvider clientProvider) {
        laboratoryClient = clientProvider.getLaboratoryClient();

        initializeLabsTable();
    }


    private void initializeLabsTable() {
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        List<Laboratory> labs = getAllLaboratories();
        if (labs != null) {
            ObservableList<Laboratory> labObs = FXCollections.observableArrayList(labs);
            labsTable.setItems(labObs);
        }
    }

    private List<Laboratory> getAllLaboratories() {
        return laboratoryClient.getLaboratories();
    }

    public Pane getPane() {
        return pane;
    }
}
