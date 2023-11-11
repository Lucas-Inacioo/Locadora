import ModuloGerente.gui.*;
import ModuloFuncionario.gui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Aluguel de Veículos");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        TabPane tabPane = new TabPane();

        Tab veiculoTab = VeiculoTab.createVeiculoTab();
        Tab clienteTab = ClienteTab.createClientesTab();
        Tab reservaTab = ReservaTab.createReservaTab();
        Tab configuracoesTab = ConfiguracoesTab.createCofiguracoesTab();


        tabPane.getTabs().addAll(veiculoTab, clienteTab, reservaTab, configuracoesTab);
        root.setCenter(tabPane);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
