import gui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Credentials credentials = new Credentials();
        VBox loginScreen = credentials.createLoginScreen(primaryStage);
        Scene loginScene = new Scene(loginScreen, 300, 200);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
