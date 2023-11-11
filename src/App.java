import ModuloGerente.gui.*;
import ModuloFuncionario.gui.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Aluguel de Veículos");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        VBox menuBox = new VBox(10);
        Button veiculoManagementBtn = new Button("Gerenciar Veículos");
        Button customerManagementBtn = new Button("Gerenciar Clientes");
        Button rentalReservationsBtn = new Button("Realizar Reservas");
        Button operationalSettingsBtn = new Button("Configurações do Sistema");
        menuBox.getChildren().addAll(veiculoManagementBtn, customerManagementBtn, rentalReservationsBtn, operationalSettingsBtn);

        root.setLeft(menuBox);

        TabPane tabPane = new TabPane();

        Tab veiculoTab = VeiculoTab.createVeiculoTab();
        Tab clienteTab = ClienteTab.createClientesTab();
        Tab reservaTab = ReservaTab.createReservaTab();
        Tab configuracoesTab = ConfiguracoesTab.createCofiguracoesTab();


        tabPane.getTabs().addAll(veiculoTab, clienteTab, reservaTab, configuracoesTab);
        root.setCenter(tabPane);

        // Event Handling for menu buttons
        veiculoManagementBtn.setOnAction(e -> tabPane.getSelectionModel().select(veiculoTab));
        customerManagementBtn.setOnAction(e -> tabPane.getSelectionModel().select(clienteTab));
        rentalReservationsBtn.setOnAction(e -> tabPane.getSelectionModel().select(reservaTab));
        operationalSettingsBtn.setOnAction(e -> tabPane.getSelectionModel().select(configuracoesTab));

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
