package controller;

import client.ClientProvider;
import client.LaboratoryClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Laboratory;
import model.UserCredentials;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class LabsViewStudent {
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

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField labNumberTextField;

    @FXML
    private TextArea curriculaTextArea;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField searchTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorLabel;


    private LaboratoryClient laboratoryClient;
    private ObservableList<Laboratory> labObs;

    private UserCredentials userCredentials;


    public void initData(ClientProvider clientProvider, UserCredentials userCredentials) {
        laboratoryClient = clientProvider.getLaboratoryClient();
        this.userCredentials = userCredentials;

        initializeLabsTable();
        labNumberTextField.setPromptText("Lab number");
        titleTextField.setPromptText("Lab title");
        curriculaTextArea.setPromptText("Curricula");
        descriptionTextArea.setPromptText("Description");
        datePicker.setPromptText("yyyy-MM-dd");
        datePicker.setValue(LocalDate.now());

    }

    public void updateTableContents() {
        try {
            List<Laboratory> labs = laboratoryClient.getLaboratories(userCredentials);
            if (labs != null) {
                labObs = FXCollections.observableArrayList(labs);
                labsTable.setItems(labObs);
                resetError();
            }
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public Pane getPane() {
        return pane;
    }

    private void resetFields() {
        titleTextField.clear();
        labNumberTextField.clear();
        curriculaTextArea.clear();
        descriptionTextArea.clear();
        datePicker.setValue(LocalDate.now());
    }

    private void resetError() {
        errorLabel.setText("");
    }


    private void initializeLabsTable() {
        labNumberColumn.setCellValueFactory(new PropertyValueFactory<>("labNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        updateTableContents();

        // deselect row when clicked a second time
        labsTable.setRowFactory(c -> {
            final TableRow<Laboratory> row = new TableRow<>();
            row.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                final int index = row.getIndex();
                if (index >= 0 && index < labsTable.getItems().size() && labsTable.getSelectionModel().isSelected(index)  ) {
                    labsTable.getSelectionModel().clearSelection();
                    resetFields();
                    e.consume();
                }
            });
            return row;
        });
    }

    @FXML
    private void labsTableClicked(MouseEvent event) {
        Laboratory lab = labsTable.getSelectionModel().getSelectedItem();
        if (lab != null) {
            titleTextField.setText(lab.getTitle());
            labNumberTextField.setText(Integer.toString(lab.getLabNumber()));
            curriculaTextArea.setText(lab.getCurricula());
            descriptionTextArea.setText(lab.getDescription());
            datePicker.setValue(lab.getDate().toLocalDate());
        }
    }

    @FXML
    private void searchButtonClicked(ActionEvent event) {
        String keyword = searchTextField.getText();
        if (keyword != null) {
            System.out.println("search button clicked");
            try {
                List<Laboratory> labs = laboratoryClient.getLaboratories(keyword, userCredentials);
                labObs = FXCollections.observableArrayList(labs);
                labsTable.setItems(labObs);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

}
