package ModuloFuncionario.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import ModuloFuncionario.Cliente;
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

        HBox menuButtons = new HBox();
        Button btnNewReservation = new Button("Nova Reserva");
        Button btnDelReservation = new Button("Excluir Reserva");
        Button btnDelNoShow = new Button("Excluir No Shows");
        Button btnConfirmReservation = new Button("Confirmar Reserva");
        menuButtons.getChildren().addAll(btnNewReservation, btnDelReservation, btnDelNoShow, btnConfirmReservation);

        StackPane formsContainer = new StackPane();

        GridPane registerNewReservationForm = new GridPane();
        tabVeiculosDisponiveis(registerNewReservationForm, primaryStage);

        GridPane deleteReservationForm = new GridPane();
        tabDeleteReservationForm(deleteReservationForm, primaryStage);

        GridPane deleteNoShowForm = new GridPane();
        tabDeleteNoShowForm(deleteNoShowForm, primaryStage);

        GridPane confirmReservation = new GridPane();
        tabConfirmReservation(confirmReservation, primaryStage);

        formsContainer.getChildren().addAll(registerNewReservationForm, deleteReservationForm, deleteNoShowForm, confirmReservation);

        btnNewReservation.setOnAction(e -> {
            registerNewReservationForm.setVisible(true);
            registerNewReservationForm.setManaged(true);
        
            deleteReservationForm.setVisible(false);
            deleteReservationForm.setManaged(false);
            
            deleteNoShowForm.setVisible(false);
            deleteNoShowForm.setManaged(false);

            confirmReservation.setVisible(false);
            confirmReservation.setManaged(false);
        });
        
        btnDelReservation.setOnAction(e -> {
            deleteReservationForm.setVisible(true);
            deleteReservationForm.setManaged(true);
        
            registerNewReservationForm.setVisible(false);
            registerNewReservationForm.setManaged(false);
            
            deleteNoShowForm.setVisible(false);
            deleteNoShowForm.setManaged(false);
            
            confirmReservation.setVisible(false);
            confirmReservation.setManaged(false);
        });

        btnDelNoShow.setOnAction(e -> {
            tabDeleteNoShowForm(deleteNoShowForm, primaryStage);
            deleteNoShowForm.setVisible(true);
            deleteNoShowForm.setManaged(true);
        
            registerNewReservationForm.setVisible(false);
            registerNewReservationForm.setManaged(false);
            
            deleteReservationForm.setVisible(false);
            deleteReservationForm.setManaged(false);

            confirmReservation.setVisible(false);
            confirmReservation.setManaged(false);
        });

        btnConfirmReservation.setOnAction(e -> {
            confirmReservation.setVisible(true);
            confirmReservation.setManaged(true);

            deleteNoShowForm.setVisible(false);
            deleteNoShowForm.setManaged(false);
        
            registerNewReservationForm.setVisible(false);
            registerNewReservationForm.setManaged(false);
            
            deleteReservationForm.setVisible(false);
            deleteReservationForm.setManaged(false);
        });
        
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuButtons);
        borderPane.setCenter(formsContainer);
        deleteReservationForm.setVisible(false);
        deleteReservationForm.setManaged(false);
        deleteNoShowForm.setVisible(false);
        deleteNoShowForm.setManaged(false);
        confirmReservation.setVisible(false);
        confirmReservation.setManaged(false);
        
        reservationTab.setContent(borderPane);

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
        removeNode(reservationGrid);
        
        ObservableList<String> grupoList = Reserva.generateGrupoList(dataRetirada, dataDevolucao);
        ListView<String> listView = new ListView<>(grupoList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedGrupo) -> {
            displaygenerateVeiculos(reservationGrid, primaryStage, selectedGrupo, dataRetirada, dataDevolucao);
        });
    
        reservationGrid.add(listView, 0, 2, 2, 3);
    }

    private static void displaygenerateVeiculos(GridPane reservationGrid, Stage primaryStage, String selectedGrupo, String dataRetirada, String dataDevolucao) {
        removeNode(reservationGrid);
        
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
                long idLocacao = new Random().nextLong();
                if (idLocacao < 0) {idLocacao = idLocacao * -1;}

                TextInputDialog cpfDialog = new TextInputDialog();
                cpfDialog.setTitle("Cliente CPF");
                cpfDialog.setHeaderText("Enter Cliente CPF");
                cpfDialog.setContentText("CPF:");

                Optional<String> cpfResult = cpfDialog.showAndWait();
                String CPF = cpfResult.isPresent() ? cpfResult.get() : null;

                if (CPF == null || CPF.trim().isEmpty()) {
                    return;
                }

                if (!Cliente.isDuplicatedCliente(CPF)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Cliente não cadastrado");
                    alert.showAndWait();
                    return;
                }

                Boolean seguro, limpezaInt, limpezaExt;
                do {
                    List<Boolean> desiredServices = displayServices();

                    if (desiredServices == null) {
                        return;
                    }

                    seguro = desiredServices.get(0);
                    limpezaInt = desiredServices.get(1);
                    limpezaExt = desiredServices.get(2);

                    if (Cliente.isUnderAge(CPF) && seguro == false) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Cliente entre 18 e 21 anos, seguro deve ser obrigatório");
                        alert.showAndWait();
                    }
                } while (Cliente.isUnderAge(CPF) && seguro == false);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate = LocalDate.parse(dataRetirada, formatter);
                LocalDate endDate = LocalDate.parse(dataDevolucao, formatter);
                long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);

                float valorlocacao = Reserva.calculateLocacaoPrice(selectedGrupo, seguro, limpezaInt, limpezaExt, daysBetween);

                Reserva reserva = new Reserva(
                    idLocacao,
                    CPF,
                    placa,
                    dataRetirada,
                    dataDevolucao,
                    valorlocacao,
                    "ATIVA",
                    seguro,
                    limpezaInt,
                    limpezaExt
                );

                if (confirmation(reserva, Reason.CREATE)) {  
                    reserva.saveReserva();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserva feita para o CPF: " + CPF + 
                                                                        "\nValor da locação: " + valorlocacao +
                                                                        "\nID da locação: " + idLocacao);
                    alert.showAndWait();
                    removeNode(reservationGrid);
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

        return result.orElse(null);
    }

    private static void removeNode(GridPane veiculoGrid) {
        Node toRemove = null;
        for (Node node : veiculoGrid.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == 2 && 
                GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == 0) {
                toRemove = node;
                break;
            }
        }
        if (toRemove != null) {
            veiculoGrid.getChildren().remove(toRemove);
        }
    }

    private static void tabDeleteReservationForm(GridPane reservationGrid, Stage primaryStage) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);
    
        Label cpfLabel = new Label("CPF:");
        TextField cpfTextField = new TextField();
        reservationGrid.add(cpfLabel, 0, 0);
        reservationGrid.add(cpfTextField, 0, 1);
    
        Label idLocacaoLabel = new Label("ID Locação:");
        TextField idLocacaoTextField = new TextField();
        reservationGrid.add(idLocacaoLabel, 1, 0);
        reservationGrid.add(idLocacaoTextField, 1, 1);
    
        cpfTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                idLocacaoTextField.setDisable(true);
            } else {
                idLocacaoTextField.setDisable(false);
            }
        });
    
        idLocacaoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                cpfTextField.setDisable(true);
            } else {
                cpfTextField.setDisable(false);
            }
        });
    
        Button submitButton = new Button("Buscar Reservas");
        submitButton.setOnAction(e -> {
            if (!cpfTextField.getText().trim().isEmpty()) {
                ObservableList<Reserva> reservations = Reserva.getReservationsByCPF(cpfTextField.getText().trim());
                displayReservations(reservationGrid, reservations, "CANCELADA");
            }
            else if (!idLocacaoTextField.getText().trim().isEmpty()) {
                if (!Reserva.isDuplicatedReserva(idLocacaoTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Reserva não encontrada");
                    alert.showAndWait();
                    return;
                }
                if (confirmation(Reserva.getReservaByID(idLocacaoTextField.getText()), Reason.CANCELADA)) {
                    Reserva.updateReserva(idLocacaoTextField.getText(), "CANCELADA");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserva excluída");
                    alert.showAndWait();
                    removeNode(reservationGrid);
                }
            }
        });
        reservationGrid.add(submitButton, 2, 1);
    }
    
    private static void displayReservations(GridPane reservationGrid, ObservableList<Reserva> reservations, String reason) {
        removeNode(reservationGrid);
        
        TableView<Reserva> tableView = new TableView<>(reservations);
    
        TableColumn<Reserva, String> idLocacaoColumn = new TableColumn<>("ID Locação");
        idLocacaoColumn.setCellValueFactory(new PropertyValueFactory<>("identificador"));
    
        TableColumn<Reserva, String> CPFColumn = new TableColumn<>("CPF");
        CPFColumn.setCellValueFactory(new PropertyValueFactory<>("CPFCliente"));
    
        TableColumn<Reserva, String> placaColumn = new TableColumn<>("Placa");
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("Placa"));
    
        TableColumn<Reserva, String> dataRetiradaColumn = new TableColumn<>("Data de Retirada");
        dataRetiradaColumn.setCellValueFactory(new PropertyValueFactory<>("dataRetirada"));
    
        TableColumn<Reserva, String> dataDevolucaoColumn = new TableColumn<>("Data de Devolução");
        dataDevolucaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
    
        TableColumn<Reserva, String> valorLocacaoColumn = new TableColumn<>("Valor da Locação");
        valorLocacaoColumn.setCellValueFactory(new PropertyValueFactory<>("valorLocacao"));
    
        TableColumn<Reserva, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    
        tableView.getColumns().add(idLocacaoColumn);
        tableView.getColumns().add(CPFColumn);
        tableView.getColumns().add(placaColumn);
        tableView.getColumns().add(dataRetiradaColumn);
        tableView.getColumns().add(dataDevolucaoColumn);
        tableView.getColumns().add(valorLocacaoColumn);
        tableView.getColumns().add(statusColumn);
        
        statusColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(
            idLocacaoColumn.widthProperty()
            .add(CPFColumn.widthProperty())
            .add(placaColumn.widthProperty())
            .add(dataRetiradaColumn.widthProperty())
            .add(dataDevolucaoColumn.widthProperty())
            .add(valorLocacaoColumn.widthProperty())
        ));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                Reserva selectedReservation = tableView.getSelectionModel().getSelectedItem();
                
                if (confirmation(selectedReservation, Reason.valueOf(reason))) {
                    Reserva.updateReserva(selectedReservation.getIdentificador().toString(), reason);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserva atualizada!");
                    alert.showAndWait();
                    removeNode(reservationGrid);
                }
            }
        });
        reservationGrid.add(tableView, 0, 2, 7, 10);
    }

    private static void tabDeleteNoShowForm(GridPane reservationGrid, Stage primaryStage) {
        removeNode(reservationGrid);

        ObservableList<Reserva> reservations = Reserva.findNoShowCandidates(LocalDate.now());
        
        TableView<Reserva> tableView = new TableView<>(reservations);
    
        TableColumn<Reserva, String> idLocacaoColumn = new TableColumn<>("ID Locação");
        idLocacaoColumn.setCellValueFactory(new PropertyValueFactory<>("identificador"));
    
        TableColumn<Reserva, String> CPFColumn = new TableColumn<>("CPF");
        CPFColumn.setCellValueFactory(new PropertyValueFactory<>("CPFCliente"));
    
        TableColumn<Reserva, String> placaColumn = new TableColumn<>("Placa");
        placaColumn.setCellValueFactory(new PropertyValueFactory<>("Placa"));
    
        TableColumn<Reserva, String> dataRetiradaColumn = new TableColumn<>("Data de Retirada");
        dataRetiradaColumn.setCellValueFactory(new PropertyValueFactory<>("dataRetirada"));
    
        TableColumn<Reserva, String> dataDevolucaoColumn = new TableColumn<>("Data de Devolução");
        dataDevolucaoColumn.setCellValueFactory(new PropertyValueFactory<>("dataDevolucao"));
    
        TableColumn<Reserva, String> valorLocacaoColumn = new TableColumn<>("Valor da Locação");
        valorLocacaoColumn.setCellValueFactory(new PropertyValueFactory<>("valorLocacao"));
    
        TableColumn<Reserva, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    
        tableView.getColumns().add(idLocacaoColumn);
        tableView.getColumns().add(CPFColumn);
        tableView.getColumns().add(placaColumn);
        tableView.getColumns().add(dataRetiradaColumn);
        tableView.getColumns().add(dataDevolucaoColumn);
        tableView.getColumns().add(valorLocacaoColumn);
        tableView.getColumns().add(statusColumn);
        
        statusColumn.prefWidthProperty().bind(tableView.widthProperty().subtract(
            idLocacaoColumn.widthProperty()
            .add(CPFColumn.widthProperty())
            .add(placaColumn.widthProperty())
            .add(dataRetiradaColumn.widthProperty())
            .add(dataDevolucaoColumn.widthProperty())
            .add(valorLocacaoColumn.widthProperty())
        ));

        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tableView.getSelectionModel().isEmpty()) {
                Reserva selectedReserva = tableView.getSelectionModel().getSelectedItem();

                long idLocacao = selectedReserva.getIdentificador();
                if (confirmation(selectedReserva, Reason.NOSHOW)) {
                    Reserva.updateReserva(Long.toString(idLocacao), "NO-SHOW");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No-show declarado");
                    alert.showAndWait();
                    tabDeleteNoShowForm(reservationGrid, primaryStage);
                }
            }
        });

        reservationGrid.add(tableView, 0, 2, 7, 1);
    }

    private static void tabConfirmReservation(GridPane reservationGrid, Stage primaryStage) {
        reservationGrid.setVgap(10);
        reservationGrid.setHgap(10);
    
        Label cpfLabel = new Label("CPF:");
        TextField cpfTextField = new TextField();
        reservationGrid.add(cpfLabel, 0, 0);
        reservationGrid.add(cpfTextField, 0, 1);
    
        Label idLocacaoLabel = new Label("ID Locação:");
        TextField idLocacaoTextField = new TextField();
        reservationGrid.add(idLocacaoLabel, 1, 0);
        reservationGrid.add(idLocacaoTextField, 1, 1);
    
        cpfTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                idLocacaoTextField.setDisable(true);
            } else {
                idLocacaoTextField.setDisable(false);
            }
        });
    
        idLocacaoTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                cpfTextField.setDisable(true);
            } else {
                cpfTextField.setDisable(false);
            }
        });
    
        Button submitButton = new Button("Buscar Reservas");
        submitButton.setOnAction(e -> {
            if (!cpfTextField.getText().trim().isEmpty()) {
                ObservableList<Reserva> reservations = Reserva.getReservationsByCPF(cpfTextField.getText().trim());
                displayReservations(reservationGrid, reservations, "EFETIVADA");
            }
            else if (!idLocacaoTextField.getText().trim().isEmpty()) {
                if (!Reserva.isDuplicatedReserva(idLocacaoTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Reserva não encontrada");
                    alert.showAndWait();
                    return;
                }
                if (confirmation(Reserva.getReservaByID(idLocacaoTextField.getText()), Reason.EFETIVADA)) {
                    Reserva.updateReserva(idLocacaoTextField.getText(), "EFETIVADA");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Reserva efetivada");
                    alert.showAndWait();
                    removeNode(reservationGrid);
                }
            }
        });
        reservationGrid.add(submitButton, 2, 1);
    }

    private static Boolean confirmation(Reserva reserva, Reason reason) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result;

        switch (reason) {
            case Reason.NOSHOW:
                alert.setTitle("Declarar no-show?");
                alert.setHeaderText("Você está prestes a declarar no-show para a seguinte reserva:"); 
                alert.setContentText("Identificador: " + reserva.getIdentificador() + "\n" +
                                    "CPF: " + reserva.getCPFCliente() + "\n" +
                                    "Placa: " + reserva.getPlaca() + "\n" +
                                    "Data de retirada: " + reserva.getDataRetirada() + "\n" +
                                    "Data de devolução: " + reserva.getDataDevolucao() + "\n" +
                                    "Valor da locação: " + reserva.getValorLocacao() + "\n");

                result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;
            
            case Reason.CANCELADA:
                alert.setTitle("Cancelar reserva?");
                alert.setHeaderText("Você está prestes a cancelar a seguinte reserva:"); 
                alert.setContentText("Identificador: " + reserva.getIdentificador() + "\n" +
                                    "CPF: " + reserva.getCPFCliente() + "\n" +
                                    "Placa: " + reserva.getPlaca() + "\n" +
                                    "Data de retirada: " + reserva.getDataRetirada() + "\n" +
                                    "Data de devolução: " + reserva.getDataDevolucao() + "\n" +
                                    "Valor da locação: " + reserva.getValorLocacao() + "\n");

                result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;

            case Reason.CONCLUIDA:
                alert.setTitle("Efetivar devolução?");
                alert.setHeaderText("Você está prestes a efetivar a seguinte devolução:"); 
                alert.setContentText("Identificador: " + reserva.getIdentificador() + "\n" +
                                    "CPF: " + reserva.getCPFCliente() + "\n" +
                                    "Placa: " + reserva.getPlaca() + "\n" +
                                    "Data de retirada: " + reserva.getDataRetirada() + "\n" +
                                    "Data de devolução: " + reserva.getDataDevolucao() + "\n" +
                                    "Valor da locação: " + reserva.getValorLocacao() + "\n");

                result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;

            case Reason.EFETIVADA:
                alert.setTitle("Confirmar reserva?");
                alert.setHeaderText("Você está prestes a confirmar a seguinte reserva:"); 
                alert.setContentText("Identificador: " + reserva.getIdentificador() + "\n" +
                                    "CPF: " + reserva.getCPFCliente() + "\n" +
                                    "Placa: " + reserva.getPlaca() + "\n" +
                                    "Data de retirada: " + reserva.getDataRetirada() + "\n" +
                                    "Data de devolução: " + reserva.getDataDevolucao() + "\n" +
                                    "Valor da locação: " + reserva.getValorLocacao() + "\n");

                result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;

            case Reason.CREATE:
                alert.setTitle("Confirmar reserva?");
                alert.setHeaderText("Você está prestes a confirmar a seguinte reserva:"); 
                alert.setContentText("Identificador: " + reserva.getIdentificador() + "\n" +
                                    "CPF: " + reserva.getCPFCliente() + "\n" +
                                    "Placa: " + reserva.getPlaca() + "\n" +
                                    "Data de retirada: " + reserva.getDataRetirada() + "\n" +
                                    "Data de devolução: " + reserva.getDataDevolucao() + "\n" +
                                    "Valor da locação: " + reserva.getValorLocacao() + "\n");

                result = alert.showAndWait();
                return result.isPresent() && result.get() == ButtonType.OK;

            default:
                return false;
        }
        
    }
}
