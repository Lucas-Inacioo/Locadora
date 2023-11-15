package gui;

import ModuloFuncionario.gui.ClienteTab;
import ModuloFuncionario.gui.ReservaTab;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Funcionario {
    public static void loadEmployeeScreen(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        TabPane tabPane = new TabPane();

        Tab clienteTab = ClienteTab.createClientesTab();
        Tab reservaTab = ReservaTab.createVeiculosDisponiveisTab(primaryStage);

        tabPane.getTabs().addAll(clienteTab, reservaTab);
        root.setCenter(tabPane);

        primaryStage.setScene(scene);
    }
}
