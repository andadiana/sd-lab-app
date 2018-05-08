package controller;

import client.ClientProvider;
import client.LoginClient;
import client.StudentClient;
import dto.request.PasswordUpdateDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.Student;

public class SetNewPasswordView {

    @FXML
    private PasswordField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField newPasswordField2;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Label errorLabel;

    private ClientProvider clientProvider;
    private StudentClient studentClient;

    private Student student;

    public void initData(ClientProvider clientProvider, Student student) {
        this.clientProvider = clientProvider;
        this.student = student;

        studentClient = clientProvider.getStudentClient();

    }
    @FXML
    private void changePasswordButtonClicked(ActionEvent event) {
        //check if two passwords are the same
        if (newPasswordField.getText().equals(newPasswordField2.getText())) {
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setOldPassword(oldPasswordField.getText());
            passwordUpdateDTO.setNewPassword(newPasswordField.getText());

            if (!studentClient.updatePassword(student, passwordUpdateDTO)) {
                //unsuccessful update
                errorLabel.setText("Invalid credentials!");
            }
            else {
                Stage stage = (Stage) changePasswordButton.getScene().getWindow();
                stage.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Password changed successfully!");

                alert.showAndWait();
            }
        }
        else {
            errorLabel.setText("New password fields must match!");
        }
    }
}