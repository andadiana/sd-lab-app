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

import java.sql.Date;
import java.time.LocalDate;
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

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField labNumberTextField;

    @FXML
    private TextArea curriculaTextArea;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorLabel;


    private LaboratoryClient laboratoryClient;
    private ObservableList<Laboratory> labObs;


    public void initData(ClientProvider clientProvider) {
        laboratoryClient = clientProvider.getLaboratoryClient();

        initializeLabsTable();
        labNumberTextField.setPromptText("Lab number");
        titleTextField.setPromptText("Lab title");
        curriculaTextArea.setPromptText("Curricula");
        descriptionTextArea.setPromptText("Description");
        datePicker.setPromptText("yyyy-MM-dd");
        datePicker.setValue(LocalDate.now());
        resetError();

    }

    public void updateTableContents() {
        List<Laboratory> labs = laboratoryClient.getLaboratories();
        if (labs != null) {
            labObs = FXCollections.observableArrayList(labs);
            labsTable.setItems(labObs);
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
                            resetError();
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
    private void addButtonClicked(ActionEvent event) {
        try {
            Laboratory lab = parseLabFields();
            laboratoryClient.createLaboratory(lab);
            labObs.add(lab);
            resetError();
        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void updateButtonClicked(ActionEvent event) {

        Laboratory selectedLab = labsTable.getSelectionModel().getSelectedItem();
        if (selectedLab == null) {
            errorLabel.setText("Must first select a lab from the table!");
        }
        else {
            try {
                labObs.remove(selectedLab);
                Laboratory lab = parseLabFields();
                lab.setId(selectedLab.getId());
                laboratoryClient.updateLaboratory(lab);
                labObs.add(lab);
                resetError();
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    @FXML
    private void deleteButtonClicked(ActionEvent event) {
        Laboratory selectedLab = labsTable.getSelectionModel().getSelectedItem();
        if (selectedLab == null) {
            errorLabel.setText("Must first select a lab from the table!");
        }
        else {
            laboratoryClient.deleteLaboratory(selectedLab.getId());
            labObs.remove(selectedLab);
        }
    }

    private Laboratory parseLabFields() throws Exception {
        if (labNumberTextField.getText().trim().equals("") ||
                titleTextField.getText().trim().equals("") ||
                descriptionTextArea.getText().trim().equals("") ||
                curriculaTextArea.getText().trim().equals("") ||
                datePicker.getValue() == null) {
            throw new Exception("All fields must be filled!");
        }
        Laboratory lab = new Laboratory();
        int labNumber = Integer.parseInt(labNumberTextField.getText());
        lab.setLabNumber(labNumber);
        lab.setTitle(titleTextField.getText());
        lab.setCurricula(curriculaTextArea.getText());
        lab.setDescription(descriptionTextArea.getText());
        lab.setDate(Date.valueOf(datePicker.getValue()));

        System.out.println(lab);
        return lab;
    }
}
