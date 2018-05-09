package controller;

import client.ClientProvider;
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
import model.UserCredentials;

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
    private UserCredentials userCredentials;

    public void initData(ClientProvider clientProvider, Student student, UserCredentials userCredentials) {
        this.clientProvider = clientProvider;
        this.student = student;

        studentClient = clientProvider.getStudentClient();
        this.userCredentials = userCredentials;
        resetError();

    }
    @FXML
    private void changePasswordButtonClicked(ActionEvent event) {
        //check if two passwords are the same
        if (newPasswordField.getText().equals(newPasswordField2.getText())) {
            PasswordUpdateDTO passwordUpdateDTO = new PasswordUpdateDTO();
            passwordUpdateDTO.setOldPassword(oldPasswordField.getText());
            passwordUpdateDTO.setNewPassword(newPasswordField.getText());

            try {
                if (!studentClient.updatePassword(student, passwordUpdateDTO, userCredentials)) {
                    //unsuccessful update
                    errorLabel.setText("Invalid credentials!");
                } else {
                    System.out.println("updated?");
                    Stage stage = (Stage) changePasswordButton.getScene().getWindow();
                    stage.close();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Password changed successfully!");

                    alert.showAndWait();
                }
                resetError();
            } catch (Exception e) {
//                errorLabel.setText(e.getMessage());
                errorLabel.setText("Invalid password!");
            }
        }
        else {
            errorLabel.setText("New password fields must match!");
        }
    }

    private void resetError(){
        errorLabel.setText("");
    }
}
