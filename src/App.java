import gui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe principal da aplicação que estende javafx.application.Application.
 * Responsável por iniciar a interface gráfica do usuário.
 */
public class App extends Application {

    /**
     * Método start é o ponto de entrada principal para todas as aplicações JavaFX.
     * É chamado após o método init ter retornado e depois que o sistema está pronto 
     * para que a aplicação comece a ser executada.
     *
     * @param primaryStage o palco principal para esta aplicação, sobre o qual 
     * a cena criada será colocada.
     */
    @Override
    public void start(Stage primaryStage) {
        Credentials credentials = new Credentials();
        VBox loginScreen = credentials.createLoginScreen(primaryStage);
        Scene loginScene = new Scene(loginScreen, 300, 200);

        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    /**
     * Método main que é chamado para iniciar a aplicação JavaFX.
     * @param args argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
