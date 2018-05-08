package controller;

import client.ClientProvider;
import client.ClientProviderImpl;
import client.LoginClient;
import client.StudentClient;
import dto.response.LoginResponseDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Role;
import model.Student;
import model.UserCredentials;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginView {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private ClientProvider clientProvider;
    private LoginClient loginClient;
    private StudentClient studentClient;

    @FXML
    private void initialize() {

        clientProvider = new ClientProviderImpl();
        loginClient = clientProvider.getLoginClient();
        studentClient = clientProvider.getStudentClient();

        resetError();
    }


    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException{
        resetError();
        System.out.println("LOGIN");
        if (validUsernameFormat(usernameField.getText())) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            try {
                LoginResponseDTO response = loginClient.login(username, password);

                if (response == null) {
                    errorLabel.setText("Invalid credentials!");
                }
                else {
                    UserCredentials userCredentials = new UserCredentials();
                    userCredentials.setUsername(username);
                    userCredentials.setPassword(password);

                    if (response.getRole() == Role.ADMIN) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminView.fxml"));
                        Parent root = loader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Admin view");
                        stage.setScene(new Scene(root, 750, 540));
                        stage.show();

                        Stage stage1 = (Stage) loginButton.getScene().getWindow();
                        stage1.close();

                        AdminView adminView = loader.getController();
                        adminView.initData(clientProvider, userCredentials);
                    } else if (response.getRole() == Role.STUDENT) {

                        System.out.println("Is password set: " + response.isPasswordSet());
                        if (response.isPasswordSet()) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
                            Parent root = loader.load();

                            StudentView studentView = loader.getController();
                            try {
                                Student student = studentClient.getStudent(response.getUserId(), userCredentials);

                                studentView.initData(clientProvider, userCredentials, student);

                                Stage stage = new Stage();
                                stage.setTitle("Student view");
                                stage.setScene(new Scene(root, 750, 540));
                                stage.show();

                                Stage stage1 = (Stage) loginButton.getScene().getWindow();
                                stage1.close();

                                resetError();
                            } catch (Exception e) {
                                errorLabel.setText(e.getMessage());
                            }
                        } else {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SetNewPasswordView.fxml"));
                            Parent root = loader.load();


                            SetNewPasswordView setNewPasswordView = loader.getController();

                            try {
                                Student student = studentClient.getStudent(response.getUserId(), userCredentials);

                                setNewPasswordView.initData(clientProvider, student, userCredentials);
                                resetError();
                                passwordField.clear();

                                Stage stage = new Stage();
                                stage.setTitle("Set new password view");
                                stage.setScene(new Scene(root, 500, 400));
                                stage.show();
                            } catch (Exception e) {
                                errorLabel.setText(e.getMessage());
                            }
                        }
                    }

                }
                resetError();
            } catch (Exception e) {
//                errorLabel.setText(e.getMessage());
                errorLabel.setText("Invalid credentials!");
            }
        }
        else {
            errorLabel.setText("Invalid email format!");
        }

    }

    private void resetError(){
        errorLabel.setText("");
    }

    private boolean validUsernameFormat(String username) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
