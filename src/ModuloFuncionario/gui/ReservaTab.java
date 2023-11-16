package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ModuloFuncionario.Reserva;
import ModuloGerente.Veiculo;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
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

    private static void tabVeiculosDisponiveis(GridPane reservationGrid, Stage primaryStage) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);

        Label dataRetiradaLabel = new Label("Data de Retirada do Veículo:");
        DatePicker dataRetiradaPicker = new DatePicker();
        reservationGrid.add(dataRetiradaLabel, 0, 0);
        reservationGrid.add(dataRetiradaPicker, 0, 1);

        Label dataDevolucaoLabel = new Label("Data de Devolução do Veículo:");
        DatePicker dataDevolucaoPicker = new DatePicker();
        reservationGrid.add(dataDevolucaoLabel, 1, 0);
        reservationGrid.add(dataDevolucaoPicker, 1, 1);

        Button submitButton = new Button("Buscar Veículos Disponíveis");
        submitButton.setOnAction(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dataRetiradaField = dataRetiradaPicker.getValue().format(formatter);
            String dataDevolucaoField = dataDevolucaoPicker.getValue().format(formatter);
            
            displayGrupos(reservationGrid, primaryStage, dataRetiradaField, dataDevolucaoField);
        });
        reservationGrid.add(submitButton, 2, 1);
    }

    private static void displayGrupos(GridPane reservationGrid, Stage primaryStage, String dataRetirada, String dataDevolucao) {
        Node toRemove = null;
        for (Node node : reservationGrid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == 2 && 
                GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == 0) {
                toRemove = node;
                break;
            }
        }
        if (toRemove != null) {
            reservationGrid.getChildren().remove(toRemove);
        }
        
        ObservableList<String> grupoList = Reserva.generateGrupoList(dataRetirada, dataDevolucao);
        ListView<String> listView = new ListView<>(grupoList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedGrupo) -> {
            displaygenerateVeiculos(reservationGrid, primaryStage, selectedGrupo, dataRetirada, dataDevolucao);
        });
    
        reservationGrid.add(listView, 0, 2, 2, 3);
    }

    private static void displaygenerateVeiculos(GridPane reservationGrid, Stage primaryStage, String selectedGrupo, String dataRetirada, String dataDevolucao) {
        Node toRemove = null;
        for (Node node : reservationGrid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == 2 && 
                GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == 0) {
                toRemove = node;
                break;
            }
        }
        if (toRemove != null) {
            reservationGrid.getChildren().remove(toRemove);
        }
        
        ObservableList<Veiculo> veiculosList = Reserva.generateVeiculosList(selectedGrupo);
        TableView<Veiculo> tableView = new TableView<>(veiculosList);
    
        TableColumn<Veiculo, String> placaColumn = new TableColumn<>("Placa");
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("placa"));
    
        TableColumn<Veiculo, String> marcaColumn = new TableColumn<>("Marca");
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
    
        TableColumn<Veiculo, String> modeloColumn = new TableColumn<>("Modelo");
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
    
        TableColumn<Veiculo, String> corColumn = new TableColumn<>("Cor");
        corColumn.setCellValueFactory(new PropertyValueFactory<>("cor"));
    
        TableColumn<Veiculo, String> anoFabricacaoColumn = new TableColumn<>("Ano");
        anoFabricacaoColumn.setCellValueFactory(new PropertyValueFactory<>("anoFabricação"));
    
        TableColumn<Veiculo, String> nomeGrupoColumn = new TableColumn<>("Grupo");
        nomeGrupoColumn.setCellValueFactory(new PropertyValueFactory<>("nomeGrupo"));
    
        TableColumn<Veiculo, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    
        tableView.getColumns().add(placaColumn);
        tableView.getColumns().add(marcaColumn);
        tableView.getColumns().add(modeloColumn);
        tableView.getColumns().add(corColumn);
        tableView.getColumns().add(anoFabricacaoColumn);
        tableView.getColumns().add(nomeGrupoColumn);
        tableView.getColumns().add(statusColumn);
        
        statusColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(
            placaColumn.widthProperty()
            .add(marcaColumn.widthProperty())
            .add(modeloColumn.widthProperty())
            .add(corColumn.widthProperty())
            .add(anoFabricacaoColumn.widthProperty())
            .add(nomeGrupoColumn.widthProperty())
        ));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                Veiculo selectedCar = tableView.getSelectionModel().getSelectedItem();
                
                String placa = selectedCar.getPlaca();
                long generatedLong = new Random().nextLong();
                if (generatedLong < 0) {generatedLong = generatedLong * -1;}

                TextInputDialog cpfDialog = new TextInputDialog();
                cpfDialog.setTitle("Cliente CPF");
                cpfDialog.setHeaderText("Enter Cliente CPF");
                cpfDialog.setContentText("CPF:");

                Optional<String> cpfResult = cpfDialog.showAndWait();
                String CPF = cpfResult.isPresent() ? cpfResult.get() : null;
                
                List<Boolean> desiredServices = displayServices();
                Boolean seguro = desiredServices.get(0);
                Boolean limpezaInt = desiredServices.get(1);
                Boolean limpezaExt = desiredServices.get(2);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(dataRetirada, formatter);
                LocalDate endDate = LocalDate.parse(dataDevolucao, formatter);
                long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

                float valorlocacao = Reserva.calculateLocacaoPrice(selectedGrupo, seguro, limpezaInt, limpezaExt, daysBetween);

                if (CPF != null && !CPF.trim().isEmpty()) {
                    Reserva reserva = new Reserva(
                        generatedLong,
                        CPF,
                        placa,
                        dataRetirada,
                        dataDevolucao,
                        valorlocacao,
                        "a"
                    );
                    reserva.saveReserva();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserva feita para o CPF: " + CPF);
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "CPF Não pode ser vazio");
                    alert.showAndWait();
                }
            }
        });

        reservationGrid.add(tableView, 0, 2, 7, 10);
    }

    private static List<Boolean> displayServices() {
        Dialog<List<Boolean>> dialog = new Dialog<>();
        dialog.setTitle("Serviços");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        CheckBox checkBox1 = new CheckBox("Seguro");
        CheckBox checkBox2 = new CheckBox("Limpeza Interna");
        CheckBox checkBox3 = new CheckBox("Limpeza Externa");

        VBox checkBoxGroup = new VBox(10);
        checkBoxGroup.getChildren().addAll(checkBox1, checkBox2, checkBox3);
        dialog.getDialogPane().setContent(checkBoxGroup);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return Arrays.asList(checkBox1.isSelected(), checkBox2.isSelected(), checkBox3.isSelected());
            }
            return null;
        });

        Optional<List<Boolean>> result = dialog.showAndWait();

        return result.orElseGet(() -> Arrays.asList(false, false, false));
    }
}
