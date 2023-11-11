package ModuloGerente.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ModuloGerente.Veiculo;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class VeiculoTab {

    public static Tab createVeiculoTab() {
        Tab VeiculoTab = new Tab("Veículos");
        GridPane vehicleGrid = new GridPane();
        registerNewVeiculo(vehicleGrid);
        VeiculoTab.setContent(vehicleGrid);

        return VeiculoTab;
    }

    private static void registerNewVeiculo(GridPane vehicleGrid) {
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
}
