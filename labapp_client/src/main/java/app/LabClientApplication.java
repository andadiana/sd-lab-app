package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LabClientApplication extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StudentView.fxml"));
        Parent root = loader.load();

        stage = primaryStage;
        stage.setTitle("Lab Application");
        stage.setScene(new Scene(root, 380, 280));
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

}
