package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.Optional;

import ModuloGerente.Veiculo;

public class VeiculoTab {

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

            String placa = placaField.getText();
            String marca = marcaField.getText();
            String modelo = modeloField.getText();
            String cor = corField.getText();
            String ano = anoFabricacaoField.getText();
            String grupo = nomeGrupoField.getValue();

            if (Veiculo.duplicatedVeiculo(placa)) {
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
                    placa.toUpperCase().replaceAll(" ", ""),
                    marca.toUpperCase().replaceAll(" ", ""),
                    modelo.toUpperCase().replaceAll(" ", ""),
                    cor.toUpperCase().replaceAll(" ", ""),
                    ano.toUpperCase().replaceAll(" ", ""),
                    grupo.toUpperCase().replaceAll(" ", ""),
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

    private static void deleteVeiculoForm(GridPane veiculoGrid) {
        veiculoGrid.setVgap(10);
        veiculoGrid.setHgap(10);

        Label placaLabel = new Label("Placa:");
        TextField placaField = new TextField();
        veiculoGrid.add(placaLabel, 0, 0);
        veiculoGrid.add(placaField, 1, 0);

        Label motivoLabel = new Label("Motivo da Exclusão:");
        TextField motivoField = new TextField();
        veiculoGrid.add(motivoLabel, 0, 1);
        veiculoGrid.add(motivoField, 1, 1);

        Button submitButton = new Button("Excluir Veículo");
        submitButton.setOnAction(e -> {
            Veiculo.deleteVeiculo(placaField.getText(), motivoField.getText());
        });
        veiculoGrid.add(submitButton, 1, 2);
    }
    
    private static Boolean confirmation(String placa, String marca, String modelo,
                                        String cor, String anoFabricacao, String nomeGrupo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); 
        alert.setTitle("Criar veículo?");
        alert.setHeaderText(null); 
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
}