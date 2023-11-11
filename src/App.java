import ModuloGerente.*;
import ModuloFuncionario.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sistema de Gerenciamento de Aluguel de Veículos");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        VBox menuBox = new VBox(10);
        Button vehicleManagementBtn = new Button("Gerenciar Veículos");
        Button customerManagementBtn = new Button("Gerenciar Clientes");
        Button rentalReservationsBtn = new Button("Realizar Reservas");
        Button operationalSettingsBtn = new Button("Configurações do Sistema");
        menuBox.getChildren().addAll(vehicleManagementBtn, customerManagementBtn, rentalReservationsBtn, operationalSettingsBtn);

        root.setLeft(menuBox);

        TabPane tabPane = new TabPane();

        Tab vehicleTab = new Tab("Veículos");
        GridPane vehicleGrid = new GridPane();
        tabVeiculos(vehicleGrid);
        vehicleTab.setContent(vehicleGrid);

        Tab customerTab = new Tab("Clientes");
        GridPane customerGrid = new GridPane();
        tabClientes(customerGrid);
        customerTab.setContent(customerGrid);

        Tab reservationTab = new Tab("Reservas");
        GridPane reservationGrid = new GridPane();
        tabReservas(reservationGrid);
        reservationTab.setContent(reservationGrid);

        Tab operationalTab = new Tab("Configurações");
        GridPane operationalGrid = new GridPane();
        tabConfiguracoes(operationalGrid);
        operationalTab.setContent(operationalGrid);
        
        tabPane.getTabs().addAll(vehicleTab, customerTab, reservationTab, operationalTab);
        root.setCenter(tabPane);

        // Event Handling for menu buttons
        vehicleManagementBtn.setOnAction(e -> tabPane.getSelectionModel().select(vehicleTab));
        customerManagementBtn.setOnAction(e -> tabPane.getSelectionModel().select(customerTab));
        rentalReservationsBtn.setOnAction(e -> tabPane.getSelectionModel().select(reservationTab));
        operationalSettingsBtn.setOnAction(e -> tabPane.getSelectionModel().select(operationalTab));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void tabVeiculos(GridPane vehicleGrid) {
        vehicleGrid.setVgap(10);
        vehicleGrid.setHgap(10);

        Label placaLabel = new Label("Placa:");
        TextField placaField = new TextField();
        vehicleGrid.add(placaLabel, 0, 0);
        vehicleGrid.add(placaField, 1, 0);

        Label marcaLabel = new Label("Marca:");
        TextField marcaField = new TextField();
        vehicleGrid.add(marcaLabel, 0, 1);
        vehicleGrid.add(marcaField, 1, 1);

        Label modeloLabel = new Label("Modelo:");
        TextField modeloField = new TextField();
        vehicleGrid.add(modeloLabel, 0, 2);
        vehicleGrid.add(modeloField, 1, 2);

        Label corLabel = new Label("Cor:");
        TextField corField = new TextField();
        vehicleGrid.add(corLabel, 0, 3);
        vehicleGrid.add(corField, 1, 3);

        Label anoFabricaçãoLabel = new Label("Ano de Fabricação:");
        TextField anoFabricacaoField = new TextField();
        vehicleGrid.add(anoFabricaçãoLabel, 0, 4);
        vehicleGrid.add(anoFabricacaoField, 1, 4);

        Label nomeGrupoLabel = new Label("Nome do Grupo:");
        TextField nomeGrupoField = new TextField();
        vehicleGrid.add(nomeGrupoLabel, 0, 5);
        vehicleGrid.add(nomeGrupoField, 1, 5);

        Label carStatusLabel = new Label("Status do Veículo:");
        TextField carStatusField = new TextField();
        vehicleGrid.add(carStatusLabel, 0, 6);
        vehicleGrid.add(carStatusField, 1, 6);

        Button submitButton = new Button("Registrar Veículo");
        submitButton.setOnAction(e -> {
            Veiculo veiculo = new Veiculo(
                placaField.getText(),
                marcaField.getText(),
                modeloField.getText(),
                corField.getText(),
                anoFabricacaoField.getText(),
                nomeGrupoField.getText(),
                carStatusField.getText()
            );
            veiculo.saveVeiculo();
        });
        vehicleGrid.add(submitButton, 1, 7);
    }

    private static void tabClientes(GridPane customerGrid) {
        customerGrid.setVgap(10);
        customerGrid.setHgap(10);

        Label CPFLabel = new Label("CPF:");
        TextField CPFField = new TextField();
        customerGrid.add(CPFLabel, 0, 0);
        customerGrid.add(CPFField, 1, 0);

        Label nomeClienteLabel = new Label("Nome do Cliente:");
        TextField nomeClienteField = new TextField();
        customerGrid.add(nomeClienteLabel, 0, 1);
        customerGrid.add(nomeClienteField, 1, 1);

        Label dataNascimentoLabel = new Label("Data de Nascimento:");
        TextField dataNascimentoField = new TextField();
        customerGrid.add(dataNascimentoLabel, 0, 2);
        customerGrid.add(dataNascimentoField, 1, 2);

        Label emailLabel = new Label("E-Mail:");
        TextField emailField = new TextField();
        customerGrid.add(emailLabel, 0, 3);
        customerGrid.add(emailField, 1, 3);

        Label numeroCelularLabel = new Label("Celular:");
        TextField numeroCelularField = new TextField();
        customerGrid.add(numeroCelularLabel, 0, 4);
        customerGrid.add(numeroCelularField, 1, 4);

        Button submitButton = new Button("Registrar Cliente");
        submitButton.setOnAction(e -> {
            Cliente cliente = new Cliente(
                CPFField.getText(),
                nomeClienteField.getText(),
                dataNascimentoField.getText(),
                emailField.getText(),
                numeroCelularField.getText()
            );
            cliente.saveCliente();
        });
        customerGrid.add(submitButton, 1, 7);
    }

    private static void tabReservas(GridPane reservationGrid) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);

        Label identificadorLabel = new Label("Identificador:");
        TextField identificadorField = new TextField();
        reservationGrid.add(identificadorLabel, 0, 0);
        reservationGrid.add(identificadorField, 1, 0);

        Label CPFClienteLabel = new Label("CPF do Cliente:");
        TextField CPFClienteField = new TextField();
        reservationGrid.add(CPFClienteLabel, 0, 1);
        reservationGrid.add(CPFClienteField, 1, 1);

        Label placaLabel = new Label("Placa do Veículo:");
        TextField placaField = new TextField();
        reservationGrid.add(placaLabel, 0, 2);
        reservationGrid.add(placaField, 1, 2);

        Label dataRetiradaLabel = new Label("Data de Retirada do Veículo:");
        TextField dataRetiradaField = new TextField();
        reservationGrid.add(dataRetiradaLabel, 0, 3);
        reservationGrid.add(dataRetiradaField, 1, 3);

        Label dataDevolucaoLabel = new Label("Data de Devolução do Veículo:");
        TextField dataDevolucaoField = new TextField();
        reservationGrid.add(dataDevolucaoLabel, 0, 4);
        reservationGrid.add(dataDevolucaoField, 1, 4);

        Label valorLocacaoLabel = new Label("Valor da Locação:");
        TextField valorLocacaoField = new TextField();
        reservationGrid.add(valorLocacaoLabel, 0, 5);
        reservationGrid.add(valorLocacaoField, 1, 5);

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();
        reservationGrid.add(statusLabel, 0, 6);
        reservationGrid.add(statusField, 1, 6);

        Button submitButton = new Button("Registrar Reserva");
        submitButton.setOnAction(e -> {
            Reserva reserva = new Reserva(
                identificadorField.getText(),
                CPFClienteField.getText(),
                placaField.getText(),
                dataRetiradaField.getText(),
                dataDevolucaoField.getText(),
                valorLocacaoField.getText(),
                statusField.getText()
            );
            reserva.saveReserva();
        });
        reservationGrid.add(submitButton, 1, 7);
    }

    private static void tabConfiguracoes(GridPane operationalGrid) {
        operationalGrid.setVgap(10);
        operationalGrid.setHgap(10);

        Label nomeGrupoLabel = new Label("Nome do Grupo:");
        TextField nomeGrupoField = new TextField();
        operationalGrid.add(nomeGrupoLabel, 0, 0);
        operationalGrid.add(nomeGrupoField, 1, 0);

        Label valorDiariaLabel = new Label("Valor da Diária:");
        TextField valorDiariaField = new TextField();
        operationalGrid.add(valorDiariaLabel, 0, 1);
        operationalGrid.add(valorDiariaField, 1, 1);

        Label valorTanqueLabel = new Label("Valor Para Encher o Tanque:");
        TextField valorTanqueField = new TextField();
        operationalGrid.add(valorTanqueLabel, 0, 2);
        operationalGrid.add(valorTanqueField, 1, 2);

        Label valorLimpezaExtLabel = new Label("Valor da Limpeza Externa:");
        TextField valorLimpezaExtField = new TextField();
        operationalGrid.add(valorLimpezaExtLabel, 0, 3);
        operationalGrid.add(valorLimpezaExtField, 1, 3);

        Label valorLimpezaintLabel = new Label("Valor da Limpeza Interna:");
        TextField valorLimpezaintField = new TextField();
        operationalGrid.add(valorLimpezaintLabel, 0, 4);
        operationalGrid.add(valorLimpezaintField, 1, 4);

        Label diariaSeguroLabel = new Label("Valor da Diária do Seguro:");
        TextField diariaSeguroField = new TextField();
        operationalGrid.add(diariaSeguroLabel, 0, 5);
        operationalGrid.add(diariaSeguroField, 1, 5);

        Button submitButton = new Button("Registrar Configurações");
        submitButton.setOnAction(e -> {
            Configuracoes configuracoes = new Configuracoes(
                nomeGrupoField.getText(),
                valorDiariaField.getText(),
                valorTanqueField.getText(),
                valorLimpezaExtField.getText(),
                valorLimpezaintField.getText(),
                diariaSeguroField.getText()
            );
            configuracoes.saveConfiguracoes();
        });
        operationalGrid.add(submitButton, 1, 7);
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
