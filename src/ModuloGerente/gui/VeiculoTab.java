package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;

import java.util.Optional;

import ModuloGerente.Veiculo;

/**
 * Classe que representa a aba de veículos na interface gráfica do módulo gerente.
 */
public class VeiculoTab {

    /**
     * Cria e retorna uma aba de veículos.
     * @return A aba de veículos criada.
     */
    public static Tab createVeiculoTab() {
        Tab veiculoTab = new Tab("Veículos");
        
        HBox menuButtons = new HBox();
        Button btnNewVeiculo = new Button("Novo Veículo");
        Button btnDelVeiculo = new Button("Excluir Veículo");
        menuButtons.getChildren().addAll(btnNewVeiculo, btnDelVeiculo);
        
        StackPane formsContainer = new StackPane();

        GridPane registerNewVeiculoForm = new GridPane();
        registerNewVeiculo(registerNewVeiculoForm);

        GridPane deleteVeiculoForm = new GridPane();
        deleteVeiculoForm(deleteVeiculoForm);

        formsContainer.getChildren().addAll(registerNewVeiculoForm, deleteVeiculoForm);

        btnNewVeiculo.setOnAction(e -> {
            registerNewVeiculoForm.setVisible(true);
            registerNewVeiculoForm.setManaged(true);
        
            deleteVeiculoForm.setVisible(false);
            deleteVeiculoForm.setManaged(false);
        });
        
        btnDelVeiculo.setOnAction(e -> {
            deleteVeiculoForm.setVisible(true);
            deleteVeiculoForm.setManaged(true);
        
            registerNewVeiculoForm.setVisible(false);
            registerNewVeiculoForm.setManaged(false);
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuButtons);
        borderPane.setCenter(formsContainer);
        deleteVeiculoForm.setVisible(false);
        deleteVeiculoForm.setManaged(false);
        
        veiculoTab.setContent(borderPane);

        return veiculoTab;
    }

    /**
     * Configura o layout e os componentes da aba de cadastro de novos veículos.
     * @param veiculoGrid O grid pane onde os componentes serão adicionados.
     */
    private static void registerNewVeiculo(GridPane veiculoGrid) {
        veiculoGrid.setVgap(10);
        veiculoGrid.setHgap(10);

        Label placaLabel = new Label("Placa:");
        TextField placaField = new TextField();
        veiculoGrid.add(placaLabel, 0, 0);
        veiculoGrid.add(placaField, 1, 0);

        Label marcaLabel = new Label("Marca:");
        TextField marcaField = new TextField();
        veiculoGrid.add(marcaLabel, 0, 1);
        veiculoGrid.add(marcaField, 1, 1);

        Label modeloLabel = new Label("Modelo:");
        TextField modeloField = new TextField();
        veiculoGrid.add(modeloLabel, 0, 2);
        veiculoGrid.add(modeloField, 1, 2);

        Label corLabel = new Label("Cor:");
        TextField corField = new TextField();
        veiculoGrid.add(corLabel, 0, 3);
        veiculoGrid.add(corField, 1, 3);

        Label anoFabricaçãoLabel = new Label("Ano de Fabricação:");
        TextField anoFabricacaoField = new TextField();
        veiculoGrid.add(anoFabricaçãoLabel, 0, 4);
        veiculoGrid.add(anoFabricacaoField, 1, 4);

        Label nomeGrupoLabel = new Label("Nome do Grupo:");
        ComboBox<String> nomeGrupoField = new ComboBox<>();
        nomeGrupoField.getItems().addAll("BASICO", "PADRAO", "PREMIUM");
        nomeGrupoField.setValue("BASICO");
        veiculoGrid.add(nomeGrupoLabel, 0, 5);
        veiculoGrid.add(nomeGrupoField, 1, 5);

        Button submitButton = new Button("Registrar Veículo");
        submitButton.setOnAction(e -> {
            Boolean placaEmpty = false, marcaEmpty = false, modeloEmpty = false, corEmpty = false, anoEmpty = false;
            String emptyFields = "Os seguintes campos precisam ser preenchidos: ";

            if (placaField.getText().trim().isEmpty()) {
                placaEmpty = true;
                emptyFields += "| PLACA |";
            }
            if (marcaField.getText().trim().isEmpty()) {
                marcaEmpty = true;
                emptyFields += "| MARCA |";
            }
            if (modeloField.getText().trim().isEmpty()) {
                modeloEmpty = true;
                emptyFields += "| MODELO |";
            }   
            if (corField.getText().trim().isEmpty()) {
                corEmpty = true;
                emptyFields += "| COR |";
            }
            if (anoFabricacaoField.getText().trim().isEmpty()) {
                anoEmpty = true;
                emptyFields += "| ANO |";
            }

            if (placaEmpty || marcaEmpty || modeloEmpty || corEmpty || anoEmpty) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Campos Faltando");
                alert.setHeaderText(null); 
                alert.setContentText(emptyFields);

                alert.showAndWait();
                return;
            }

            String placa = placaField.getText().replaceAll(" ", "");
            String marca = marcaField.getText().replaceAll(" ", "");
            String modelo = modeloField.getText().replaceAll(" ", "");
            String cor = corField.getText().replaceAll(" ", "");
            String ano = anoFabricacaoField.getText().replaceAll(" ", "");
            String grupo = nomeGrupoField.getValue().replaceAll(" ", "");

            if (Veiculo.isDuplicatedVeiculo(placa)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Veículo duplicado");
                alert.setHeaderText(null); 
                alert.setContentText("Veículo de placa " + placa + " já cadastrado, favor verificar!");

                alert.showAndWait();
                return;
            }

            if (!Veiculo.isValidPlaca(placa)) {
                Alert alert = new Alert(Alert.AlertType.WARNING); 
                alert.setTitle("Placa inválida");
                alert.setHeaderText(null); 
                alert.setContentText("A placa inserida precisa seguir o padrão LLLNLNN");

                alert.showAndWait();
                return;
            }

            if (confirmation(placa, marca, modelo, cor, ano, grupo)) {
                Veiculo veiculo = new Veiculo(
                    placa.toUpperCase(),
                    marca.toUpperCase(),
                    modelo.toUpperCase(),
                    cor.toUpperCase(),
                    ano.toUpperCase(),
                    grupo.toUpperCase(),
                    "DISPONIVEL"
                );
                veiculo.saveVeiculo();

                Alert alert = new Alert(Alert.AlertType.INFORMATION); 
                alert.setTitle("Sucesso");
                alert.setHeaderText(null); 
                alert.setContentText("Veículo de placa " + placa + " cadastrado com sucesso!");

                alert.showAndWait();
            }
        });
        veiculoGrid.add(submitButton, 1, 7);
    }

    /**
     * Configura o layout e os componentes da aba de exclusão de veículos.
     * @param veiculoGrid O grid pane onde os componentes serão adicionados.
     */
    private static void deleteVeiculoForm(GridPane veiculoGrid) {
        veiculoGrid.setVgap(10);
        veiculoGrid.setHgap(10);

        Label placaLabel = new Label("Placa:");
        TextField placaField = new TextField();
        veiculoGrid.add(placaLabel, 0, 0);
        veiculoGrid.add(placaField, 1, 0);

        addDeleteSubmit(veiculoGrid);
    }
    
    /**
     * Mostra a lista de veículos disponíveis para exclusão.
     * @param veiculoGrid O grid pane onde os veículos serão exibidos.
     */
    private static void displaygenerateVeiculos(GridPane veiculoGrid) {
        removeNode(veiculoGrid);
        
        ObservableList<Veiculo> veiculosList = Veiculo.generateVeiculosDisponiveis();
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
                String marca = selectedCar.getMarca();
                String modelo = selectedCar.getModelo();
                String cor = selectedCar.getCor();
                String ano = selectedCar.getAnoFabricação();
                String grupo = selectedCar.getNomeGrupo();

                String selectedMotivo = motivoDialog();
                if (selectedMotivo == null) {
                    return;
                }

                if (confirmation(placa, marca, modelo, cor, ano, grupo, selectedMotivo)) {
                    Veiculo.deleteVeiculo(placa, selectedMotivo);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veículo de placa: " + placa + " excluído com sucesso!");
                    alert.showAndWait();
                    removeNode(veiculoGrid);
                    addDeleteSubmit(veiculoGrid);
                }
            }
        });

        veiculoGrid.add(tableView, 0, 2, 7, 10);
    }

    /**
     * Exibe um diálogo para escolher o motivo da exclusão de um veículo.
     * @return O motivo selecionado.
     */
    private static String motivoDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Select Motivo");

        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, ButtonType.CANCEL);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Venda", "Roubo/Furto", "Acidente com perda total", "Outro");
        comboBox.setValue("Venda");

        dialog.getDialogPane().setContent(comboBox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == confirmButtonType) {
                return comboBox.getValue();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        return result.orElse(null);
    }

    /**
     * Exibe um alerta de confirmação para a criação ou exclusão de um veículo.
     * @param placa A placa do veículo.
     * @param marca A marca do veículo.
     * @param modelo O modelo do veículo.
     * @param cor A cor do veículo.
     * @param anoFabricacao O ano de fabricação do veículo.
     * @param nomeGrupo O grupo ao qual o veículo pertence.
     * @return Verdadeiro se o usuário confirmar a ação.
     */
    private static Boolean confirmation(String placa, String marca, String modelo,
                                        String cor, String anoFabricacao, String nomeGrupo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Criar veículo?");
        alert.setHeaderText("Você está prestes a criar o seguinte veículo: "); 
        alert.setContentText(
                    "Placa: " + placa + "\n" +
                    "Marca: " + marca + "\n" +
                    "Modelo: " + modelo + "\n" +
                    "Cor: " + cor + "\n" +
                    "Ano: " + anoFabricacao + "\n" +
                    "Grupo: " + nomeGrupo
                    );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Exibe um alerta de confirmação para a exclusão de um veículo, incluindo o motivo.
     * @param placa A placa do veículo.
     * @param marca A marca do veículo.
     * @param modelo O modelo do veículo.
     * @param cor A cor do veículo.
     * @param anoFabricacao O ano de fabricação do veículo.
     * @param nomeGrupo O grupo ao qual o veículo pertence.
     * @param motivo O motivo da exclusão.
     * @return Verdadeiro se o usuário confirmar a ação.
     */
    private static Boolean confirmation(String placa, String marca, String modelo,
                                        String cor, String anoFabricacao, String nomeGrupo, String motivo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Excluir veículo?");
        alert.setHeaderText("Você está prestes a excluir o seguinte veículo: "); 
        alert.setContentText(
                    "Placa: " + placa + "\n" +
                    "Marca: " + marca + "\n" +
                    "Modelo: " + modelo + "\n" +
                    "Cor: " + cor + "\n" +
                    "Ano: " + anoFabricacao + "\n" +
                    "Grupo: " + nomeGrupo + "\n" +
                    "Motivo da exclusão: " + motivo
                    );

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Remove um nó específico do grid pane.
     * @param veiculoGrid O grid pane do qual o nó será removido.
     */
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

    /**
     * Adiciona um botão de submissão para a exclusão de veículos ao grid pane.
     * @param veiculoGrid O grid pane onde o botão será adicionado.
     */
    private static void addDeleteSubmit(GridPane veiculoGrid) {
        int placaFieldRow = 0;
        int placaFieldColumn = 1;

        TextField placaField = findTextFieldByGridPosition(veiculoGrid, placaFieldRow, placaFieldColumn);

        Button submitButton = new Button("Excluir Veículo");
        submitButton.setOnAction(e -> {
            if (placaField.getText().trim().isEmpty()) {
                displaygenerateVeiculos(veiculoGrid);
            }
            else {
                String placa = placaField.getText();
                if (!Veiculo.isDuplicatedVeiculo(placa)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING); 
                    alert.setTitle("Veículo não encontrado");
                    alert.setHeaderText("Veículo não encontrado"); 
                    alert.setContentText("Veículo de placa " + placa + " não encontrado, favor verificar!");

                    alert.showAndWait();
                    return;
                }

                if (!Veiculo.isLocado(placa)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING); 
                    alert.setTitle("Veículo locado ");
                    alert.setHeaderText("Veículo locado"); 
                    alert.setContentText("Veículo de placa " + placa + " encontra-se, no momento, locado!");

                    alert.showAndWait();
                    return;
                }

                if (!Veiculo.isDisponivel(placa)) {
                    Alert alert = new Alert(Alert.AlertType.WARNING); 
                    alert.setTitle("Veículo indisponível ");
                    alert.setHeaderText("Veículo indisponível"); 
                    alert.setContentText("Veículo de placa " + placa + " encontra-se, no momento, indisponível!");

                    alert.showAndWait();
                    return;
                }

                String selectedMotivo = motivoDialog();
                if (selectedMotivo == null) {
                    return;
                }

                Veiculo veiculo = Veiculo.getVeiculoByPlaca(placa);
                if (veiculo != null && confirmation(veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getCor(), veiculo.getAnoFabricação(), veiculo.getNomeGrupo(), selectedMotivo)) {
                    Veiculo.deleteVeiculo(placa, selectedMotivo);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veículo de placa: " + placa + " excluído com sucesso!");
                    alert.showAndWait();

                    placaField.setText("");
                }
            }
        });
        veiculoGrid.add(submitButton, 0, 2);
    }

    /**
     * Encontra um campo de texto no grid pane de acordo com a posição da linha e coluna.
     * @param gridPane O grid pane onde o campo de texto está localizado.
     * @param row A linha onde o campo de texto está localizado.
     * @param column A coluna onde o campo de texto está localizado.
     * @return O campo de texto encontrado ou nulo se não for encontrado.
     */
    private static TextField findTextFieldByGridPosition(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row 
                && GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column 
                && node instanceof TextField) {
                return (TextField) node;
            }
        }
        return null;
    }
}