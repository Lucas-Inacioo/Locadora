package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;

import ModuloFuncionario.Reserva;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class ReservaTab {

    public static Tab createVeiculosDisponiveisTab(Stage primaryStage) {
        Tab reservationTab = new Tab("Reservas");
        GridPane reservationGrid = new GridPane();
        tabVeiculosDisponiveis(reservationGrid, primaryStage);
        reservationTab.setContent(reservationGrid);

        return reservationTab;
    }

    public static Tab createReservaTab(Stage primaryStage) {
        Tab reservationTab = new Tab("Reservas");
        GridPane reservationGrid = new GridPane();
        tabReservas(reservationGrid, primaryStage);
        reservationTab.setContent(reservationGrid);

        return reservationTab;
    }

    private static void tabVeiculosDisponiveis(GridPane reservationGrid, Stage primaryStage) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);

        Label dataRetiradaLabel = new Label("Data de Retirada do Veículo:");
        DatePicker dataRetiradaPicker = new DatePicker();
        reservationGrid.add(dataRetiradaLabel, 0, 0);
        reservationGrid.add(dataRetiradaPicker, 1, 1);

        Label dataDevolucaoLabel = new Label("Data de Devolução do Veículo:");
        DatePicker dataDevolucaoPicker = new DatePicker();
        reservationGrid.add(dataDevolucaoLabel, 2, 0);
        reservationGrid.add(dataDevolucaoPicker, 3, 1);

        Button submitButton = new Button("Buscar Veículos Disponíveis");
        submitButton.setOnAction(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataRetiradaField = dataRetiradaPicker.getValue().format(formatter);
            String dataDevolucaoField = dataDevolucaoPicker.getValue().format(formatter);
            
            displayGrupos(reservationGrid, primaryStage, dataRetiradaField, dataDevolucaoField);
        });
        reservationGrid.add(submitButton, 1, 7);
    }

    private static void tabReservas(GridPane reservationGrid, Stage primaryStage) {
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

        Label valorLocacaoLabel = new Label("Valor da Locação:");
        TextField valorLocacaoField = new TextField();
        reservationGrid.add(valorLocacaoLabel, 0, 5);
        reservationGrid.add(valorLocacaoField, 1, 5);

        Label statusLabel = new Label("Status:");
        TextField statusField = new TextField();
        reservationGrid.add(statusLabel, 0, 6);
        reservationGrid.add(statusField, 1, 6);

        /*
        Button submitButton = new Button("Registrar Reserva");
        submitButton.setOnAction(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataRetiradaField = dataRetiradaPicker.getValue().format(formatter);
            String dataDevolucaoField = dataDevolucaoPicker.getValue().format(formatter);

            Reserva reserva = new Reserva(
                identificadorField.getText(),
                CPFClienteField.getText(),
                placaField.getText(),
                dataRetiradaField,
                dataDevolucaoField,
                valorLocacaoField.getText(),
                statusField.getText()
            );
            reserva.saveReserva();
        });
        reservationGrid.add(submitButton, 1, 7);
        */
    }

    private static void displayGrupos(GridPane reservationGrid, Stage primaryStage, String dataRetirada, String dataDevolucao) {
        ObservableList<String> grupoList = Reserva.generateGrupoList();
        ListView<String> listView = new ListView<>(grupoList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedGrupo) -> {
            displayPlacas(reservationGrid, primaryStage, selectedGrupo);
        });
    
        reservationGrid.add(listView, 0, 2, 2, 1);
    }

    private static void displayPlacas(GridPane reservationGrid, Stage primaryStage, String selectedGrupo) {
        ObservableList<String> placaList = Reserva.generatePlacaList(selectedGrupo);
        ListView<String> listView = new ListView<>(placaList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue); // TODO: Change selection logic
        });
    
        reservationGrid.add(listView, 0, 2, 2, 1);
    }
}
