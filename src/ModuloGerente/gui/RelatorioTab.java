package ModuloGerente.gui;

import gui.Relatorio;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RelatorioTab {
    public static Tab createRelatorioTab(Stage primaryStage) {
        Tab relatorioTab = new Tab("Relatórios");
        GridPane relatorioGrid = new GridPane();
        tabRelatorio(relatorioGrid, primaryStage);
        relatorioTab.setContent(relatorioGrid);

        return relatorioTab;
    }

    private static void tabRelatorio(GridPane relatorioGrid, Stage primaryStage) {
        Label label = new Label("Gerar relatório de:");
        ComboBox<String> comboBox = new ComboBox<>();
        
        comboBox.getItems().addAll("Clientes", "Veículos", "Locações/Reservas");
        comboBox.setValue("Clientes");

        relatorioGrid.add(label, 0, 0);        
        relatorioGrid.add(comboBox, 1, 0);

        Button submitButton = new Button("Gerar Relatório");
        submitButton.setOnAction(e -> {
            String desiredRelatorio = comboBox.getValue();
            if ("Clientes".equals(desiredRelatorio)) {
                Relatorio.generateRelatorioClientes(primaryStage);
            } else if ("Veículos".equals(desiredRelatorio)) {
                Relatorio.generateRelatorioVeiculos(primaryStage);
            } else if ("Locações/Reservas".equals(desiredRelatorio)) {
                Relatorio.generateRelatorioReservas(primaryStage);
            }
        });
        relatorioGrid.add(submitButton, 1, 1);
    }
}

