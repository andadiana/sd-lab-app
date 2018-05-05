package controller;

import client.ClientProvider;
import client.LaboratoryClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Laboratory;

import java.sql.Date;
import java.util.List;



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
