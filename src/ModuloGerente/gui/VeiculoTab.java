package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
        nomeGrupoField.getItems().addAll("BASICO", "PADRAO", "VIP");
        nomeGrupoField.setValue("BASICO");
        veiculoGrid.add(nomeGrupoLabel, 0, 5);
        veiculoGrid.add(nomeGrupoField, 1, 5);

        Label carStatusLabel = new Label("Status do Veículo:");
        TextField carStatusField = new TextField();
        veiculoGrid.add(carStatusLabel, 0, 6);
        veiculoGrid.add(carStatusField, 1, 6);

        Button submitButton = new Button("Registrar Veículo");
        submitButton.setOnAction(e -> {
            Veiculo veiculo = new Veiculo(
                placaField.getText(),
                marcaField.getText(),
                modeloField.getText(),
                corField.getText(),
                anoFabricacaoField.getText(),
                nomeGrupoField.getValue(),
                carStatusField.getText()
            );
            veiculo.saveVeiculo();
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
}